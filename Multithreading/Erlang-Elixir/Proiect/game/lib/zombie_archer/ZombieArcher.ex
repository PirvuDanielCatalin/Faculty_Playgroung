defmodule ZombieArcher do
  ##################################
  ### Zombie Archer Actions ###
  ##################################
  def zombie_archer_attack(dragon_pid, dmg) do
    IO.inspect("Zombie Archerul ataca cu Shot de #{dmg} damage.")
    send(dragon_pid, {:from_za_zombie_archer_attack, dmg})
  end

  ##################################
  ### Receive Loop ###
  ##################################
  def receive_loop(za_hp, dragon_pid) do
    receive do
      {:from_zas_shot, dmg} ->
        ZombieArcher.zombie_archer_attack(dragon_pid, dmg)
        ZombieArcher.receive_loop(za_hp, dragon_pid)

      {:from_d_dragon_attack, dmg} ->
        if za_hp - dmg > 0 do
          ZombieArcher.receive_loop(za_hp - dmg, dragon_pid)
        else
          send(dragon_pid, {:from_za_zombie_archer_lost, self()})
        end
    end
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:dragon_pid, dragon_pid} -> ZombieArcher.receive_loop(100, dragon_pid)
    end
  end
end
