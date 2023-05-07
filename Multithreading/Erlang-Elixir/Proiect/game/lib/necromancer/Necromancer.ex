defmodule Necromancer do
  ##################################
  ### Utils ###
  ##################################
  def stop_knights_and_archers(
        zombie_knight_pids,
        zombie_knight_strategy_pids,
        zombie_archer_pids,
        zombie_archer_strategy_pids
      ) do
    Enum.each(zombie_knight_pids, fn zombie_knight_pid ->
      Process.exit(zombie_knight_pid, "Game Over")
    end)

    Enum.each(zombie_knight_strategy_pids, fn zombie_knight_strategy_pid ->
      Process.exit(zombie_knight_strategy_pid, "Game Over")
    end)

    Enum.each(zombie_archer_pids, fn zombie_archer_pid ->
      Process.exit(zombie_archer_pid, "Game Over")
    end)

    Enum.each(zombie_archer_strategy_pids, fn zombie_archer_strategy_pid ->
      Process.exit(zombie_archer_strategy_pid, "Game Over")
    end)
  end

  ##################################
  ### Necromancer Actions ###
  ##################################
  def necromancer_won(main_pid, remaining_hp) do
    send(main_pid, {:necromancer_won, remaining_hp})
  end

  ##################################
  ### Receive Loop ###
  ##################################
  def receive_loop(
        n_hp,
        main_pid,
        dragon_pid,
        zombie_knight_pids,
        zombie_knight_strategy_pids,
        zombie_archer_pids,
        zombie_archer_strategy_pids
      ) do
    receive do
      {:from_ns_anti_zombie_bolt, dmg} ->
        IO.inspect("Necromancerul ataca cu Anti Zombie Bolt de #{dmg} damage.")
        send(dragon_pid, {:from_n_necromancer_attack, dmg})

        Necromancer.receive_loop(
          n_hp,
          main_pid,
          dragon_pid,
          zombie_knight_pids,
          zombie_knight_strategy_pids,
          zombie_archer_pids,
          zombie_archer_strategy_pids
        )

      {:from_ns_summon_zombie_knight, _x} ->
        IO.inspect("Necromancerul summoneaza un Zombie Knight.")

        zk_pid = spawn(ZombieKnight, :run, [])
        send(zk_pid, {:dragon_pid, dragon_pid})

        zks_pid = spawn(ZombieKnightStrategy, :run, [])
        send(zks_pid, {:zombie_knight_pid, zk_pid})

        send(dragon_pid, {:from_n_new_zombie_knight_enemy, zk_pid})

        Necromancer.receive_loop(
          n_hp,
          main_pid,
          dragon_pid,
          zombie_knight_pids ++ [zk_pid],
          zombie_knight_strategy_pids ++ [zks_pid],
          zombie_archer_pids,
          zombie_archer_strategy_pids
        )

      {:from_ns_summon_zombie_archer, _x} ->
        IO.inspect("Necromancerul summoneaza un Zombie Archer.")

        za_pid = spawn(ZombieArcher, :run, [])
        send(za_pid, {:dragon_pid, dragon_pid})

        zas_pid = spawn(ZombieArcherStrategy, :run, [])
        send(zas_pid, {:zombie_archer_pid, za_pid})

        send(dragon_pid, {:from_n_new_zombie_archer_enemy, za_pid})

        Necromancer.receive_loop(
          n_hp,
          main_pid,
          dragon_pid,
          zombie_knight_pids,
          zombie_knight_strategy_pids,
          zombie_archer_pids ++ [za_pid],
          zombie_archer_strategy_pids ++ [zas_pid]
        )

      {:from_d_dragon_attack, dmg} ->
        if n_hp - dmg > 0 do
          Necromancer.receive_loop(
            n_hp - dmg,
            main_pid,
            dragon_pid,
            zombie_knight_pids,
            zombie_knight_strategy_pids,
            zombie_archer_pids,
            zombie_archer_strategy_pids
          )
        else
          IO.inspect("Sa-i fie tarana usoara Necromancerului!")
          send(dragon_pid, {:from_n_necromancer_lost, 0})

          Necromancer.stop_knights_and_archers(
            zombie_knight_pids,
            zombie_knight_strategy_pids,
            zombie_archer_pids,
            zombie_archer_strategy_pids
          )
        end

      {:from_d_dragon_lost, _x} ->
        Necromancer.necromancer_won(main_pid, n_hp)

        Necromancer.stop_knights_and_archers(
          zombie_knight_pids,
          zombie_knight_strategy_pids,
          zombie_archer_pids,
          zombie_archer_strategy_pids
        )
    end
  end

  ##################################
  ### Main ###
  ##################################
  def run() do
    receive do
      {:main_pid, main_pid} ->
        receive do
          {:dragon_pid, dragon_pid} ->
            Necromancer.receive_loop(10000, main_pid, dragon_pid, [], [], [], [])
        end
    end
  end
end
