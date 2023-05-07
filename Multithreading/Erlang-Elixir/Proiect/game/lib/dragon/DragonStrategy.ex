defmodule DragonStrategy do
  ##################################
  ### Dragon Attacks ###
  ##################################
  def whiptail(d_pid) do
    dmg = Enum.random(50..100)

    :timer.sleep(5)

    send(d_pid, {:from_ds_whiptail, dmg})
  end

  def dragon_breath(d_pid) do
    dmg = Enum.random(50..150)

    :timer.sleep(5)

    send(d_pid, {:from_ds_dragon_breath, dmg})
  end

  def attack(d_pid) do
    type = Enum.random(1..5)

    case type do
      1 -> DragonStrategy.dragon_breath(d_pid)
      _ -> DragonStrategy.whiptail(d_pid)
    end

    DragonStrategy.attack(d_pid)
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:dragon_pid, d_pid} -> DragonStrategy.attack(d_pid)
    end
  end
end
