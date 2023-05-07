defmodule Game do
  ##################################
  ### Receive Game Status ###
  ##################################
  def receive_game_status() do
    receive do
      {:dragon_won, dragon_remaining_hp} ->
        IO.inspect("A castigat dragonul si a ramas cu #{dragon_remaining_hp} cantitate de viata.")

      {:necromancer_won, necromancer_remaining_hp} ->
        IO.inspect(
          "A castigat necromancerul si a ramas cu #{necromancer_remaining_hp} cantitate de viata."
        )
    end
  end

  ##################################
  ### Main ###
  ##################################
  def start do
    main_pid = self()

    d_pid = spawn(Dragon, :run, [])
    send(d_pid, {:main_pid, main_pid})

    ds_pid = spawn(DragonStrategy, :run, [])
    send(ds_pid, {:dragon_pid, d_pid})

    n_pid = spawn(Necromancer, :run, [])
    send(n_pid, {:main_pid, main_pid})

    ns_pid = spawn(NecromancerStrategy, :run, [])
    send(ns_pid, {:necromancer_pid, n_pid})

    send(d_pid, {:necromancer_pid, n_pid})
    send(n_pid, {:dragon_pid, d_pid})

    IO.inspect("PID Main: #{inspect(main_pid)}")
    IO.inspect("PID Dragon: #{inspect(d_pid)}")
    IO.inspect("PID DragonStrategy: #{inspect(ds_pid)}")
    IO.inspect("PID Necromancer: #{inspect(n_pid)}")
    IO.inspect("PID NecromancerStrategy: #{inspect(ns_pid)}")

    Game.receive_game_status()

    Process.exit(d_pid, "Game Over")
    Process.exit(ds_pid, "Game Over")
    Process.exit(n_pid, "Game Over")
    Process.exit(ns_pid, "Game Over")

    IO.puts("Game Over")
  end
end

# Game.start()
