defmodule Ex2 do
  def count(text, word) do
    splits = text |> String.split(word) |> length()
    IO.puts  splits - 1
  end
end
