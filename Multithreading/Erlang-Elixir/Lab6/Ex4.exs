defmodule Ex4 do
  def count_all(text) do
    text |> String.split([" ", ","], trim: true) |> Enum.frequencies_by(&String.downcase/1)
  end
end
