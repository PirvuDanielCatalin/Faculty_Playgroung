defmodule Ex3 do
  def count(text, word) do
    splits = String.downcase(text) |> String.split(String.downcase(word)) |> length()
    IO.puts  splits - 1
  end
end
