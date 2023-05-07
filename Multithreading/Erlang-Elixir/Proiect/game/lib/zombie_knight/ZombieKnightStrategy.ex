defmodule ZombieKnightStrategy do
  ##################################
  ### Zombie Knight Attacks ###
  ##################################
  def sword_slash(zk_pid) do
    dmg = Enum.random(20..50)

    :timer.sleep(5)

    send(zk_pid, {:from_zks_sword_slash, dmg})
  end

  def attack(zk_pid) do
    ZombieKnightStrategy.sword_slash(zk_pid)
    ZombieKnightStrategy.attack(zk_pid)
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:zombie_knight_pid, zk_pid} ->
        ZombieKnightStrategy.attack(zk_pid)
    end
  end
end
