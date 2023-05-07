defmodule ZombieKnight do
  ##################################
  ### Zombie Knight Actions ###
  ##################################
  def zombie_knight_attack(dragon_pid, dmg) do
    IO.inspect("Zombie Knightul ataca cu Sword Slash de #{dmg} damage.")
    send(dragon_pid, {:from_zk_zombie_knight_attack, dmg})
  end

  ##################################
  ### Receive Loop ###
  ##################################
  def receive_loop(zk_hp, dragon_pid) do
    receive do
      {:from_zks_sword_slash, dmg} ->
        ZombieKnight.zombie_knight_attack(dragon_pid, dmg)
        ZombieKnight.receive_loop(zk_hp, dragon_pid)

      {:from_d_dragon_attack, dmg} ->
        if zk_hp - dmg > 0 do
          ZombieKnight.receive_loop(zk_hp - dmg, dragon_pid)
        else
          send(dragon_pid, {:from_zk_zombie_knight_lost, self()})
        end
    end
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:dragon_pid, dragon_pid} -> ZombieKnight.receive_loop(600, dragon_pid)
    end
  end
end
