defmodule ZombieArcherStrategy do
  ##################################
  ### Zombie Archer Attacks ###
  ##################################
  def shot(za_pid) do
    dmg = Enum.random(100..200)

    :timer.sleep(10)

    send(za_pid, {:from_zas_shot, dmg})
  end

  def attack(za_pid) do
    ZombieArcherStrategy.shot(za_pid)
    ZombieArcherStrategy.attack(za_pid)
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:zombie_archer_pid, za_pid} ->
        ZombieArcherStrategy.attack(za_pid)
    end
  end
end
