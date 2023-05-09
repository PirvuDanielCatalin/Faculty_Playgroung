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


def suma(A, B):
    result = []
    length1A = len(A)  # Nr de linii din A
    length1B = len(B)  # Nr de linii din B
    i = 0
    j = 0
    while i < length1A and j < length1B:
        linieA = A[i][0][1]  # Valoarea liniei din matricea rara A
        linieB = B[j][0][1]  # Valoarea liniei din matricea rara B

        # Daca valorile liniilor nu coincid putem adauga direct una din ele in matricea rezultat
        if linieA < linieB:
            result.append(A[i])
            i += 1
        else:
            if linieB < linieA:
                result.append(B[j])
                j += 1
            else:
                # Daca ele coincid trebuie sa le parcurgem si sa calculam elementele liniei sumateł
                temp = []
                length2A = len(A[i])
                length2B = len(B[j])
                ii = 0
                jj = 0
                while ii < length2A and jj < length2B:
                    coloanaA = A[i][ii][2]
                    coloanaB = B[j][jj][2]
                    if coloanaA < coloanaB:
                        temp.append(A[i][ii])
                        ii += 1
                    else:
                        if coloanaB < coloanaA:
                            temp.append(B[j][jj])
                            jj += 1
                        else:  # coloanaA == coloanaB
                            valoareA = A[i][ii][0]
                            valoareB = B[j][jj][0]
                            temp.append(
                                (valoareA + valoareB, linieA, coloanaA))
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
    reMat = []  # Matricea transpusa
    for lista in Mat:  # Pentru fiecare lista de tip linie (vector de tuple)
        for element in lista:  # Pentru fiecare tuplu din lista
            coloana = element[2]  # Preiau coloana din tuplu
            # Preiau lungimea actuala a matricii transpuse = nr de coloane facute pana in prezent
            # len([]) = 0
            l = len(reMat)

            # Cautam pozitia in matricea transpusa reMat a listei de tip coloana corespunzatoare,
            # adica lista care are doar tuple ce au a treia componenta egala cu variabila coloana
            i = 0
            ok = 0
            while i < l:
                ok = 1
                if reMat[i][0][2] == coloana:
                    # Daca am gasit o lista de tip coloana care respecta ce e scris mai sus, ma opresc,
                    # astfel cand ies din while i va fi indicele in matricea transpusa reMat
                    # a listei de tip coloana unde va fi inserat noul element
                    break
                else:
                    # Daca nu respecta mergem mai departe pana cand verificam toate listele de tip coloana
                    # existente pana in pasul curent in reMat
                    i += 1

            # Daca i=0 inseamna ca nu ai intrat in while si chestia asta se poate intampla
            # doar la primul element(tuplu) citit din Mat
            # Daca i=l inseamna ca a iesit din while si nu a gasit inca o lista tip coloana
            # corespunzatoare celui de-al 3-lea camp din element(tuplu)
            if ok == 0 or i == l:  # Nu exista inca in lista reMat o lista corespunzatoare coloanei "coloana"
                temp = []  # Se face o noua lista de tip coloana
                # Elementul(tuplu) se insereaza in lista mai sus creata
                temp.append(element)
                # Intreaga lista se insereaza in matricea transpusa reMat
                reMat.append(temp)
            else:
                # Daca i e undeva la mijloc elementul(tuplul) se insereaza in lista de tip coloana
                # de la pozitia i in matricea transpusa
                reMat[i].append(element)

    reMat = sorted(reMat, key=lambda x: x[0][2])
    # Sortez lista de liste de tip coloana dupa coloane
    # adica din fiecare lista interioara iau primu element si vad a treia componeneta din tuplu
    return reMat


def inmultire(A, B):
    result = []
    length1A = len(A)  # Nr de linii din A
    length1B = len(B)  # Nr de linii din B
    i = 0
    while i < length1A:
        temp = []
        linia = A[i][0][1]  # Valoarea liniei din matricea rara A
        j = 0
        while j < length1B:
            coloana = B[j][0][2]  # Valoarea coloanei din matricea rara B
            suma = 0
            length2A = len(A[i])
            length2B = len(B[j])
            ii = 0
            jj = 0

            # Parcurgem cele doua liste de tuple - linia din A si coloana din B -
            # si calculam elementele listei rezultat
            while ii < length2A and jj < length2B:
                if A[i][ii][2] < B[j][jj][1]:
                    ii += 1
                else:
                    if A[i][ii][2] > B[j][jj][1]:
                        jj += 1
                    else:
                        # Adunam la suma produsul elementelor doar in cazul in care linia din A corespunde coloanei din B,
                        # in rest produsul elementelor ar fi 0
                        suma += A[i][ii][0]*B[j][jj][0]
                        ii += 1
                        jj += 1
            if suma != 0:
                temp.append((suma, linia, coloana))
            j += 1
        if len(temp) != 0:
            result.append(temp)
        i += 1
    return result


def inmultireCuX(A):
    result = []
    length1A = len(A)
    i = 0
    while i < length1A:
        suma = 0
        length2A = len(A[i])
        ii = 0
        while ii < length2A:
            coloana = A[i][ii][2]
            valoare = A[i][ii][0]
            suma += valoare*(2019 - coloana)
            ii += 1
        result.append(suma)
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

    # Citirea matricilor A si B

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

# Suma

    sumaAsiB = suma(structMatrixA, structMatrixB)
    # print("Suma dintre A si B calculata:")
    # print(sumaAsiB)
    # print("\n")

    aplusb = open("aplusb.txt")
    n3, structMatrixAplusB, b3 = getAandBfromTxt(aplusb)
    # print("Suma dintre A si B citita din fisier:")
    # print(structMatrixAplusB)
    # print("\n")

    print("Cele doua matrici 'Suma' sunt identice? ",
          checkMequalsN(sumaAsiB, structMatrixAplusB))
    print("\n")
#

# Inmultirea A * B

    inmultireAsiB = inmultire(structMatrixA, restructMatrix(structMatrixB))
    # print("Inmultirea dintre A si B calculata:")
    # print(inmultireAsiB)
    # print("\n")

    aorib = open("aorib.txt")
    n4, structMatrixAoriB, b4 = getAandBfromTxt(aorib)
    # print("Inmultirea dintre A si B citita din fisier:")
    # print(structMatrixAoriB)
    # print("\n")

    print("Cele doua matrici 'Inmultire' sunt identice? ",
          checkMequalsN(inmultireAsiB, structMatrixAoriB))
    print("\n")
#

# Inmultirea A * X

    # print("b1:")
    # print(b1)
    # print("\n")

    inmultireAsiX = inmultireCuX(structMatrixA)
    # print("Inmultirea dintre A si X:")
    # print(inmultireAsiX)
    # print("\n")

    print("Cei doi vectori 'Inmultire' sunt identici? ",
          checkMequalsb(inmultireAsiX, b1))
    print("\n")
#

# Inmultirea B * X

    # print("b2:")
    # print(b2)
    # print("\n")

    inmultireBsiX = inmultireCuX(structMatrixB)
    # print("Inmultirea dintre B si X:")
    # print(inmultireBsiX)
    # print("\n")

    print("Cei doi vectori 'Inmultire' sunt identici? ",
          checkMequalsb(inmultireBsiX, b2))
    print("\n")
#
