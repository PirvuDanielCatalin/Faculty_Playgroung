defmodule Dragon do
  ##################################
  ### Dragon Actions ###
  ##################################
  def dragon_attack(type, dmg, necromancer_pid, zombie_knight_pids, zombie_archer_pids) do
    case type do
      :whiptail ->
        zombie_knights_no = length(zombie_knight_pids)
        zombie_archers_no = length(zombie_archer_pids)

        if zombie_knights_no > 0 do
          idx = Enum.random(0..(zombie_knights_no - 1))
          victim = Enum.at(zombie_knight_pids, idx)

          IO.inspect("Dragonul ataca cu Whiptail de #{dmg} damage.")
          send(victim, {:from_d_dragon_attack, dmg})
        else
          if zombie_archers_no > 0 do
            necromancer_or_archer = Enum.random([:necromancer, :archer])

            case necromancer_or_archer do
              :necromancer ->
                victim = necromancer_pid

                IO.inspect("Dragonul ataca cu Whiptail de #{dmg} damage.")
                send(victim, {:from_d_dragon_attack, dmg})

              :archer ->
                idx = Enum.random(0..(zombie_archers_no - 1))
                victim = Enum.at(zombie_archer_pids, idx)

                IO.inspect("Dragonul ataca cu Whiptail de #{dmg} damage.")
                send(victim, {:from_d_dragon_attack, dmg})
            end
          else
            victim = necromancer_pid

            IO.inspect("Dragonul ataca cu Whiptail de #{dmg} damage.")
            send(victim, {:from_d_dragon_attack, dmg})
          end
        end

      :dragon_breath ->
        IO.inspect("Dragonul ataca cu Dragon Breath de #{dmg} damage.")

        send(necromancer_pid, {:from_d_dragon_attack, dmg})

        Enum.each(zombie_knight_pids, fn zombie_knight_pid ->
          send(zombie_knight_pid, {:from_d_dragon_attack, dmg})
        end)

        Enum.each(zombie_archer_pids, fn zombie_archer_pid ->
          send(zombie_archer_pid, {:from_d_dragon_attack, dmg})
        end)
    end
  end

  def dragon_won(main_pid, remaining_hp) do
    send(main_pid, {:dragon_won, remaining_hp})
  end

  ##################################
  ### Receive Loop ###
  ##################################
  def receive_loop(d_hp, main_pid, necromancer_pid, zombie_knight_pids, zombie_archer_pids) do
    receive do
      {:from_ds_whiptail, dmg} ->
        Dragon.dragon_attack(:whiptail, dmg, necromancer_pid, zombie_knight_pids, zombie_archer_pids)
        Dragon.receive_loop(d_hp, main_pid, necromancer_pid, zombie_knight_pids, zombie_archer_pids)

      {:from_ds_dragon_breath, dmg} ->
        Dragon.dragon_attack(:dragon_breath, dmg, necromancer_pid, zombie_knight_pids, zombie_archer_pids)
        Dragon.receive_loop(d_hp, main_pid, necromancer_pid, zombie_knight_pids, zombie_archer_pids)

      {:from_n_new_zombie_knight_enemy, zk_pid} ->
        Dragon.receive_loop(d_hp, main_pid, necromancer_pid, zombie_knight_pids ++ [zk_pid], zombie_archer_pids)

      {:from_zk_zombie_knight_lost, zk_pid} ->
        Dragon.receive_loop(d_hp, main_pid, necromancer_pid, List.delete(zombie_knight_pids, zk_pid), zombie_archer_pids)

      {:from_n_new_zombie_archer_enemy, za_pid} ->
        Dragon.receive_loop(d_hp, main_pid, necromancer_pid, zombie_knight_pids, zombie_archer_pids ++ [za_pid])

      {:from_za_zombie_archer_lost, za_pid} ->
        Dragon.receive_loop(d_hp, main_pid, necromancer_pid, zombie_knight_pids, List.delete(zombie_archer_pids, za_pid))

      {:from_n_necromancer_attack, dmg} ->
        if d_hp - dmg > 0 do
          Dragon.receive_loop(d_hp - dmg, main_pid, necromancer_pid, zombie_knight_pids, zombie_archer_pids)
        else
          IO.inspect("Sa-i fie tarana usoara Dragonului!")
          send(necromancer_pid, {:from_d_dragon_lost, 0})
        end

      {:from_zk_zombie_knight_attack, dmg} ->
        if d_hp - dmg > 0 do
          Dragon.receive_loop(d_hp - dmg, main_pid, necromancer_pid, zombie_knight_pids, zombie_archer_pids)
        else
          IO.inspect("Sa-i fie tarana usoara Dragonului!")
          send(necromancer_pid, {:from_d_dragon_lost, 0})
        end

      {:from_za_zombie_archer_attack, dmg} ->
        if d_hp - dmg > 0 do
          Dragon.receive_loop(d_hp - dmg, main_pid, necromancer_pid, zombie_knight_pids, zombie_archer_pids)
        else
          IO.inspect("Sa-i fie tarana usoara Dragonului!")
          send(necromancer_pid, {:from_d_dragon_lost, 0})
        end

      {:from_n_necromancer_lost, _x} ->
        Dragon.dragon_won(main_pid, d_hp)
    end
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:main_pid, main_pid} ->
        receive do
          {:necromancer_pid, necromancer_pid} ->
            Dragon.receive_loop(1_000_000, main_pid, necromancer_pid, [], [])
        end
    end
  end
end
