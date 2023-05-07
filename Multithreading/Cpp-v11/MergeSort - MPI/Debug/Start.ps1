$Global:Words_Count = 0

function Get-WordsCount() {
    param (
        [Parameter(Mandatory = $true)] $File_Name
    )
        
    $Content = Get-Content "$PSScriptRoot\$File_Name"
    $Global:Words_Count = ($Content | Measure-Object -Word).Words
}

Get-WordsCount -File_Name "input"

$Global:Max_Process_Rank = 0
$Global:Leaves = @()
$Global:Dormant = @()

function Get-ProcessesInfo {
    param (
        [Parameter(Mandatory = $true)] $Words_Count,
        [Parameter(Mandatory = $true)] $Rank
    )

    if ($Words_Count -eq 1 -or $Words_Count -eq 2) {
        if ($Rank -gt $Global:Max_Process_Rank) {
            $Global:Max_Process_Rank = $Rank
        }
        $Global:Leaves += ([int] $Rank)
    }
    else {
        [int] $Half1 = [System.Math]::Floor($Words_Count / 2)
        [int] $Half2 = $Words_Count - $Half1

        [int] $LeftChildRank = 2 * $Rank + 1
        [int] $RightChildRank = 2 * $Rank + 2

        Get-ProcessesInfo -Words_Count $Half1 -Rank $LeftChildRank
        Get-ProcessesInfo -Words_Count $Half2 -Rank $RightChildRank
    }
}

if ($Global:Words_Count -ne 0) {
    Get-ProcessesInfo -Words_Count $Global:Words_Count -Rank 0

    $Global:Tree_Height = [Math]::Ceiling([Math]::Log($Global:Max_Process_Rank + 1) / [Math]::Log(2))
    if ([int] $Global:Words_Count -le 2) {
        $Global:Tree_Height = 1
    }

    $Last_Minus1_S = [Math]::Floor([Math]::Pow(2, $Global:Tree_Height - 1) - 1)
    $Last_Minus1_F = 2 * $Last_Minus1_S

    for ($Rank = $Last_Minus1_S; $Rank -le $Last_Minus1_F; $Rank++) {
        if (!$Global:Leaves.Contains([int] $Rank) -and [int] $Rank -ne 0) {
            $Global:Dormant += ([int] $Rank)
        }
    }

    $Last_Minus0_S = [Math]::Floor([Math]::Pow(2, $Global:Tree_Height) - 1)
    $Last_Minus0_F = 2 * $Last_Minus0_S

    for ($Rank = $Last_Minus0_S; $Rank -le $Last_Minus0_F; $Rank++) {
        if (!$Global:Leaves.Contains([int] $Rank)) {
            $Global:Dormant += ([int] $Rank)
        }
    }

    Write-Host "-------------------------------------------------"
    Write-Host "Number of words is"$Global:Words_Count
    Write-Host "Max rank is"$Global:Max_Process_Rank
    Write-Host "Tree height is"$Global:Tree_Height
    Write-Host "Array of leaves is ["($Global:Leaves -Join ", ")"]"
    Write-Host "Arry of dormant processes is ["($Global:Dormant -Join ", ")"] "
    Write-Host "-------------------------------------------------"

    mpiexec -l -n ($Global:Max_Process_Rank + 1) $PSScriptRoot\MergeSort-SendReceive.exe $Global:Max_Process_Rank $Global:Words_Count $($Global:Dormant -Join ", ")
}
