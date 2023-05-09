import os
import math


def getAandBfromTxt(a):
    matrixA = []
    b = []
    eps = 0
    for i, line in enumerate(a):
        if i == 0:
            n = int(line)
        elif i > 1 and line != '\n':
            if ',' in line:
                line = line.rstrip('\n')
                line = line.split(', ')
                line = tuple(line)
                line = [float(x) for x in line]
                line = tuple(line)
                matrixA.append(line)
            else:
                line = line.rstrip('\n')
                line = float(line)
                b.append(line)
    a.close()

    matrixA = sorted(matrixA, key=lambda x: (x[1], x[2]))

    aux = []
    structureMatrix = []
    i = 0
    for item in matrixA:
        if item[1] == i:
            if aux:
                ok = 0
                for (pos, elem) in enumerate(aux):
                    if elem[1] == item[1] and elem[2] == item[2]:
                        ok = 1
                        if not abs(item[0]-elem[0]) < eps:
                            elem = list(elem)
                            aux[pos] = (elem[0]+item[0], elem[1], elem[2])

                if ok == 0:
                    aux.append(item)
            else:
                aux.append(item)
        else:
            structureMatrix.append(aux)
            aux = []
            aux.append(item)
            i = item[1]

    return n, structureMatrix, b


def sum(A, B):
    result = []
    length1A = len(A)  # Number of lines in A
    length1B = len(B)  # Number of lines in B
    i = 0
    j = 0
    while i < length1A and j < length1B:
        lineA = A[i][0][1]  # The value of the line in A
        lineB = B[j][0][1]  # The value of the line in B

        # If the values of the lines are different, we add one of them directly in the result matrix
        if lineA < lineB:
            result.append(A[i])
            i += 1
        else:
            if lineB < lineA:
                result.append(B[j])
                j += 1
            else:
                # If they are the same, we must go through and calculate the values for the sum line
                temp = []
                length2A = len(A[i])
                length2B = len(B[j])
                ii = 0
                jj = 0
                while ii < length2A and jj < length2B:
                    columnA = A[i][ii][2]
                    columnB = B[j][jj][2]
                    if columnA < columnB:
                        temp.append(A[i][ii])
                        ii += 1
                    else:
                        if columnB < columnA:
                            temp.append(B[j][jj])
                            jj += 1
                        else:  # columnA == columnB
                            valueA = A[i][ii][0]
                            valueB = B[j][jj][0]
                            temp.append((valueA + valueB, lineA, columnA))
                            ii += 1
                            jj += 1

                while ii < length2A:
                    temp.append(A[i][ii])
                    ii += 1

                while jj < length2B:
                    temp.append(B[j][jj])
                    jj += 1

                result.append(temp)
                i += 1
                j += 1

    while i < length1A:
        result.append(A[i])
        i += 1

    while j < length1B:
        result.append(B[j])
        j += 1

    return result


def restructMatrix(Mat):
    reMat = []  # Transposed matrix
    for lista in Mat:  # For each line - vector of tuples
        for element in lista:  # For each tuple
            column = element[2]  # We take the column value from tuple
            # We take the curent length of the matrix = number of columns so far
            # len([]) = 0
            l = len(reMat)

            # We search in the transposed matrix reMat for the corresponding column type list,
            # I mean the list that has only tuples that have the third component equal to the previous column variable
            i = 0
            ok = 0
            while i < l:
                ok = 1
                if reMat[i][0][2] == column:
                    # If we find a column type list which satisfy the condition above, we stop,
                    # this way when we get out of while, i will be the index in the transposed matrix reMat
                    # of the column type list in which the new element will be inserted
                    break
                else:
                    # If not, we go further until we check all of the column type lists existing up to the current step in reMat
                    i += 1

            # If i = 0, means that we didn't pass the while condition and this could happend only for the first tuple read from Mat
            # If i = l, means that the while ended and no column type list was found coresponding for the third component of the tuple
            if ok == 0 or i == l:  # It doesn't exist in reMat a column type list coresponding to column variable
                temp = []  # We make a new column type list
                # The tuple is inserted in this new list
                temp.append(element)
                # The entire list isinserted in the transposed matrix reMat
                reMat.append(temp)
            else:
                # If i is somewhere in the middle, the tuple is inserted in the list from the index given by i in reMat
                reMat[i].append(element)

    reMat = sorted(reMat, key=lambda x: x[0][2])
    # We sort the list of column type lists by column
    # I mean we take the first element from each list and we look at his third component
    return reMat


def product(A, B):
    result = []
    length1A = len(A)  # Number of lines in A
    length1B = len(B)  # Number of lines in B
    i = 0
    while i < length1A:
        temp = []
        linia = A[i][0][1]  # The value of the line in A
        j = 0
        while j < length1B:
            column = B[j][0][2]  # The value of the column in B
            sum = 0
            length2A = len(A[i])
            length2B = len(B[j])
            ii = 0
            jj = 0

            # We go through these two lists: the line from A and the column from B, and calculate the elements of the result list
            while ii < length2A and jj < length2B:
                if A[i][ii][2] < B[j][jj][1]:
                    ii += 1
                else:
                    if A[i][ii][2] > B[j][jj][1]:
                        jj += 1
                    else:
                        # We add to the sum the product of elements only if the line from A and the column from B match,
                        # otherwise the product would be 0
                        sum += A[i][ii][0]*B[j][jj][0]
                        ii += 1
                        jj += 1
            if sum != 0:
                temp.append((sum, linia, column))
            j += 1
        if len(temp) != 0:
            result.append(temp)
        i += 1
    return result


def productWithX(A):
    result = []
    length1A = len(A)
    i = 0
    while i < length1A:
        sum = 0
        length2A = len(A[i])
        ii = 0
        while ii < length2A:
            column = A[i][ii][2]
            value = A[i][ii][0]
            sum += value*(2019 - column)
            ii += 1
        result.append(sum)
        i += 1
    return result


def checkMequalsN(A, B):
    if len(A) != len(B):
        return False
    else:
        for i in range(0, len(A)):
            if len(A[i]) != len(B[i]):
                return False
            else:
                for j in range(0, len(A[i])):
                    if A[i][j][0] != B[i][j][0] or A[i][j][1] != B[i][j][1] or A[i][j][2] != B[i][j][2]:
                        return False
    return True


def checkMequalsb(A, b):
    if len(A) != len(b):
        return False
    else:
        for i in range(0, len(A)):
            if A[i] != b[i]:
                return False
    return True


if __name__ == "__main__":

    # Reading matrices A and B

    a = open("a.txt")
    n1, structMatrixA, b1 = getAandBfromTxt(a)
    # print("A:")
    # print(structMatrixA)
    # print("\n")

    b = open("b.txt")
    n2, structMatrixB, b2 = getAandBfromTxt(b)
    # print("B:")
    # print(structMatrixB)
    # print(restructMatrix(structMatrixB))
    # print("\n")

    #

# Sum A + B

    sumAandB = sum(structMatrixA, structMatrixB)
    # print("The sum calculated:")
    # print(sumAandB)
    # print("\n")

    aplusb = open("aplusb.txt")
    n3, structMatrixAplusB, b3 = getAandBfromTxt(aplusb)
    # print("The sum read from the file:")
    # print(structMatrixAplusB)
    # print("\n")

    print("The two matrices 'Sum' are the same? ",
          checkMequalsN(sumAandB, structMatrixAplusB))
    print("\n")
#

# Product A * B

    productAandB = product(structMatrixA, restructMatrix(structMatrixB))
    # print("The product calculated:")
    # print(productAandB)
    # print("\n")

    aorib = open("aorib.txt")
    n4, structMatrixAoriB, b4 = getAandBfromTxt(aorib)
    # print("The product read fromthe file:")
    # print(structMatrixAoriB)
    # print("\n")

    print("The two matrices 'Product' are the same? ",
          checkMequalsN(productAandB, structMatrixAoriB))
    print("\n")
#

# Product A * X

    # print("b1:")
    # print(b1)
    # print("\n")

    productAandX = productWithX(structMatrixA)
    # print("The product calculated:")
    # print(productAandX)
    # print("\n")

    print("The two vectors 'Product' are the same? ",
          checkMequalsb(productAandX, b1))
    print("\n")
#

# Product B * X

    # print("b2:")
    # print(b2)
    # print("\n")

    productBandX = productWithX(structMatrixB)
    # print("The product calculated:")
    # print(productBandX)
    # print("\n")

    print("The two vectors 'Product' are the same? ",
          checkMequalsb(productBandX, b2))
    print("\n")
#
