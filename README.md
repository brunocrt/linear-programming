# linear-programming

Dado um determinado problema de otimização segue abaixo dados:

| Solução/Restrição | Valor |
|-------------------|-------|
| Max Z     | Z = 3x1 + 5x2 |
| Sujeito a | 2x1 + 4x2 <= 10|
|           | 2x1 + 4x2 <= 10|
|           | 1x1 - 1x2 <= 30|
|           | Onde X1 >= 0 e X2 >= 0|

Apos ajustes nas restrições, incluindo variáveis de folga, segue matrix com os novos dados:


| 		 | 			Z    | 	x1    |		x2   | 	xF1  | 		xF2    | 	xF3  |	b    |
|------|-----------|--------|--------|-------|-----------|-------|-------|
| BASE |    1,00   | - 3,00 | -5,00  |0,00   |	0,00     | 0,00  | 0,00  |
| y1   |  0,00     | 2,00   |	4,00   |	1,00 | 	0,00     | 0,00  | 10,00 |
| y2   | 	0,00     | 6,00   | 1,0    |	0,00 |    1,00   | 0,00  | 20,00 |
| y3   | 	0,00     | 	1,00  | -1,00  | 0,00  | 	0,00     | 1,0   | 30,00 |


Encontrar qual a solução ótima para o problema
