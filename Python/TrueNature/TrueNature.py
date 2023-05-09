import platform
import os

import yaml

import time
import re

import subprocess

import pyshark
import requests

print("\n")

# Show the OS properties and the initial config of the app
print("The current OS is " + platform.system())
print("\n")

print("The current config is :")

with open("Config.yml", "r") as file:
    full_config = yaml.full_load(file)

for item, doc in full_config.items():
    print(" " + item, ":", doc)

print("\n")

if not full_config["reports_path"]:
    print("Please set the path in config file where the reports should be stored!")
    print("\n")
    quit()

if not os.path.isdir(full_config["reports_path"]):
    print("Please set a correct actual directory path!")
    print("\n")
    quit()

# Save the config back into the file
with open("Config.yml", "w") as file:
    dump_config = yaml.dump(full_config, file)

# Show all the network interfaces
network_interfaces = subprocess.Popen(["tshark", "-D"], stdout=subprocess.PIPE, universal_newlines=True)
network_interfaces.wait()
network_interfaces_count = 0
network_interfaces_output = []

interface_choices = []
interface_choice = ""

# Print and construct the list of choices for the network interface
print("The list of your network interfaces is:")
for output in network_interfaces.stdout.readlines():
    print(output.strip())
    network_interfaces_output.append(output.strip())

    if platform.system() == "Windows":
        network_interfaces_count = network_interfaces_count + 1

    elif platform.system() == "Linux":
        tmp = re.search('(.*?\. )([a-z0-9]+)( .*)?', output.strip())
        if tmp:
            interface_choices.append(tmp.group(2))

if platform.system() == "Windows":
    interface_choices = [str(x) for x in range(1, network_interfaces_count + 1)]

print("\n")
try:
    # Make the choice of network interface
    while True:
        if interface_choice not in interface_choices:
            if platform.system() == "Windows":
                interface_choice = input(
                    "Please type the number of the interface you want to sniff the traffic from! : ")
            elif platform.system() == "Linux":
                interface_choice = input("Please type the name of the interface you want to sniff the traffic from! : ")
        else:
            tmp_choice = interface_choice
            if platform.system() == "Windows":
                tmp_choice = network_interfaces_output[int(interface_choice) - 1]

            print("\n")
            print("You chose the interface: " + tmp_choice)
            print("\n")
            break
except:
    print("\n")
    quit()

# Constructing the traffic filter
traffic_filter = "(dns.response_to)"

for iter_domain in full_config["domains_excluded"]:
    traffic_filter += " and (not dns.resp.name contains " + iter_domain + ")"

for iter_host in full_config["hosts_excluded"]:
    traffic_filter += " and (not dns.resp.name eq " + iter_host + ")"

print("The traffic filter constructed is \n" + traffic_filter)
print("\n")
print("Now the traffic capturing can start ")
print("\n")
# Start capturing the traffic and get new hosts to test
final_possible_new_exceptions = []
checked_domains = []

capture = None
current_domains_results = []
current_results = []


def dns_info(packet):
    if packet.dns.qry_name:
        current_domains_results.append(packet.dns.qry_name)
    elif packet.dns.resp_name:
        current_domains_results.append(packet.dns.resp_name)


try:
    try:
        while True:
            try:
                capture = pyshark.LiveCapture(interface_choice, display_filter=traffic_filter)

                capture.sniff(packet_count=100)

                capture.apply_on_packets(dns_info, timeout=5)
                # Generates an error after the timeout passes

            except Exception as e:
                # time.sleep(300)
                time.sleep(5)

                print("Currently extracted sites : ")
                for current_domain_result in current_domains_results:
                    print(" - " + current_domain_result)

                print("\n")

                for current_domain_result in current_domains_results:
                    current_output_dir = full_config["reports_path"] + current_domain_result

                    if not os.path.isdir(current_output_dir) and current_domain_result not in checked_domains:
                        r = requests.get("http://" + current_domain_result)
                        if r.status_code == 200:
                            # Add the extracted URL only if it is accessible

                            tmp_url_result = None
                            tmp_url = re.search('(.*?:\/\/.*?([\/\?]))(.*)', r.url)
                            if tmp_url:
                                tmp_url_result = tmp_url.group(1)
                                tmp_url_result_final_char = tmp_url.group(2)
                                if tmp_url_result_final_char == "?":
                                    tmp_url_result = tmp_url_result[:-1] + "/"

                            if tmp_url_result is not None:
                                current_results.append(
                                    {"dns": current_domain_result, "url": tmp_url_result, "path": current_output_dir})

                        checked_domains.append(current_domain_result)
                        # else: Site can"t be accessed!
                    # else: Site has been scanned before!

                if len(current_results) > 0:
                    ctr = 1
                    print("New possible data from which to extract new exceptions:")
                    print("\n")
                    for current_result in current_results:
                        print(str(ctr))
                        print(" - Dns: " + current_result["dns"])
                        print(" - Url: " + current_result["url"])
                        print(" - Path: " + current_result["path"])
                        print("\n")
                        ctr = ctr + 1
                        final_possible_new_exceptions.append(current_result["dns"])
                        print("Wapiti command will start in another process for " + current_result["url"])
                        print("\n")

                    # For each website found start the vulnerability scanner in another process
                    current_commands = []
                    current_procs = []

                    for current_result in current_results:
                        try:
                            current_command = "wapiti -u " + current_result["url"] + " -o " + current_result["path"]
                            current_proc = subprocess.Popen(current_command, shell=True, stdout=subprocess.PIPE,
                                                            stderr=subprocess.PIPE,
                                                            encoding='UTF-8')
                            current_procs.append(current_proc)
                        except Exception as e:
                            print(str(e))

                    for current_proc in current_procs:
                        current_proc.wait()
                else:
                    print("There are no new possible exceptions to be added!")

                current_domains_results = []
                current_results = []
                capture.close()

                time.sleep(10)

                print("\n")

                continue

    except:

        print("\n")
        print("The program has been stopped.")
        print("Please check the reports directory.")
        print("\n")
        if final_possible_new_exceptions is not None and len(final_possible_new_exceptions) > 0:
            print("The new possible exceptions to be added in the host area are:")
            for final_possible_new_exception in final_possible_new_exceptions:
                print(" - " + final_possible_new_exception)
        else:
            print("There are no new possible exceptions to be added in the host area")
        print("\n")

except:

    print("\n")
    print("The program has been stopped.")
    print("Please check the reports directory.")
    print("\n")
    if final_possible_new_exceptions is not None and len(final_possible_new_exceptions) > 0:
        print("The new possible exceptions to be added in the host area are:")
        for final_possible_new_exception in final_possible_new_exceptions:
            print(" - " + final_possible_new_exception)
    else:
        print("There are no new possible exceptions to be added in the host area")
    print("\n")
