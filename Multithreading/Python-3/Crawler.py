import asyncio
import aiohttp
import re

from termcolor import cprint
from urllib.parse import urljoin, urlparse


class Crawler:

    def __init__(self, initial_url, depth, no_more_then_y_in_parallel=20):
        self.initial_url = initial_url
        self.base_url = '{}://{}'.format(urlparse(self.initial_url).scheme,
                                         urlparse(self.initial_url).netloc)
        self.depth = depth
        self.seen_urls = set()
        self.session = aiohttp.ClientSession()
        self.semaphore = asyncio.BoundedSemaphore(no_more_then_y_in_parallel)

    async def request_on_url(self, url):
        cprint('Request on: {}\n'.format(url), 'blue')

        async with self.semaphore:
            tries = 3
            while True:
                if tries == 0:
                    break

                tries = tries - 1

                try:
                    async with self.session.get(url, timeout=30, ssl=False) as response:

                        first_chunk_was_taken = False
                        data = None
                        list_of_found_urls = []

                        while True:
                            chunk = await response.content.read(2000)

                            if not first_chunk_was_taken:
                                data = chunk.decode().replace('\n', ' ').replace('\r', '')
                                first_chunk_was_taken = True

                            if not chunk:
                                break

                            decoded = chunk.decode("utf-8", "replace").replace('\n', ' ').replace('\r', '')

                            regex_pattern = r'<a[ ]+href="(.+?)".+?>.*?<\/a>'
                            anchors = re.findall(regex_pattern, decoded)

                            if anchors != []:
                                for href in anchors:
                                    url = urljoin(self.base_url, href)

                                    if url not in self.seen_urls and url.startswith(self.base_url):
                                        list_of_found_urls.append(url)
      
                        return data, list_of_found_urls
                except Exception as e:
                    cprint(
                        'Exception caught when trying to get HTML data from URL {}: {}\n'.format(url, e), 'red')

    async def single_extract(self, url):
        data, list_of_found_urls = await self.request_on_url(url)

        set_of_found_urls = set()

        if data:
            for url in list_of_found_urls:
                set_of_found_urls.add(url)

        return url, data, set_of_found_urls

    async def multiple_extract(self, go_through):
        futures = []
        results = []

        for url in go_through:
            if url in self.seen_urls:
                continue

            self.seen_urls.add(url)
            futures.append(self.single_extract(url))

        for future in asyncio.as_completed(futures):
            try:
                results.append((await future))
            except Exception as e:
                cprint('Exception caught while waiting for the future to finish: {}\n'.format(e), 'yellow')

        return results

    def parser(self, data):
        regex_pattern = r'<title>(.*?)<\/title>'
        title = re.findall(regex_pattern, data)[0]
        return {'title': title}

    async def crawl_start(self):
        search_area = [self.initial_url]

        results = []

        for depth in range(self.depth + 1):
            load = await self.multiple_extract(search_area)

            search_area = []

            for url, data, found_urls in load:
                data = self.parser(data)
                results.append((depth, url, data))
                search_area.extend(found_urls)

        await self.session.close()

        return results


if __name__ == '__main__':
    url = 'https://a1.ro'

    crawler = Crawler(url, 3)

    future = asyncio.Task(crawler.crawl_start())

    loop = asyncio.get_event_loop()

    loop.run_until_complete(future)

    result = future.result()

    cprint("\n##########################################################", "green")
    cprint('Length of the result is {}\n'.format(len(result)), "green")
    cprint('A sample of the result is ', "green")
    for res in result[: 20]:
        cprint(res, "green")
    cprint("##########################################################", "green")
