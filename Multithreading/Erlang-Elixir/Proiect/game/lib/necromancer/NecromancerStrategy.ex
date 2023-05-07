defmodule NecromancerStrategy do
  ##################################
  ### Necromancer Attacks ###
  ##################################
  def anti_zombie_bolt(n_pid) do
    dmg = Enum.random(0..1000)
    :timer.sleep(12)
    send(n_pid, {:from_ns_anti_zombie_bolt, dmg})
  end

  def summon_zombie_knight(n_pid) do
    :timer.sleep(20)
    send(n_pid, {:from_ns_summon_zombie_knight, 0})
  end

  def summon_zombie_archer(n_pid) do
    :timer.sleep(20)
    send(n_pid, {:from_ns_summon_zombie_archer, 0})
  end

  def attack(n_pid) do
    type = Enum.random(1..3)

    case type do
      1 -> NecromancerStrategy.anti_zombie_bolt(n_pid)
      2 -> NecromancerStrategy.summon_zombie_knight(n_pid)
      3 -> NecromancerStrategy.summon_zombie_archer(n_pid)
    end

    NecromancerStrategy.attack(n_pid)
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:necromancer_pid, n_pid} -> NecromancerStrategy.attack(n_pid)
    end
  end
end
