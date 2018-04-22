package Tetris;

/**
 * A {@code MatrixHelper} osztálya, mely az alakzatok mozgatásaihoz szükséges mátrixműveleteket
 * valósítja meg.
 */
public class MatrixHelper
{

	/**
	 * Visszaadja a {@code int} mátrix sorainak a számát. 
	 * 
	 * @param matrix a mátrix
	 * @return a mátrix sorainak a száma
	 */
	public static int getRowNum(int[][] matrix)
	{
		return matrix.length;	//hány sora van
	}
	
	/**
	 * Visszaadja a {@code int} mátrix oszlopainak a számát. 
	 * 
	 * @param matrix a mátrix
	 * @return a mátrix oszlopainak a száma
	 */
	public static int getColNum(int[][] matrix)
	{
		if(getRowNum(matrix) == 0)
		{
			return 0;	//ha nincs sora, nincs oszlopa
		}
		return matrix[0].length;	//hány oszlopa van
		//Annyi oszlopa van a mátrixnak, ahány eleme van az első sorának.
	}

	/**
	 * A paraméterül adott {@code int} mátrix tartalmának megjelenítése. A ciklus végigmegy a
	 * {@code int} mátrix összes elemén és kiíratja azokat.
	 * 
	 * @param matrix a megjelenítendő mátrix
	 */
	public static void matrixShower(int[][] matrix)
	{
		for(int i = 0; i < getRowNum(matrix); i++)
		{
			for(int j = 0; j < getColNum(matrix); j++)
			{
				//kiíratom az elemet
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * A paraméterül adott {@code int} mátrix transzponálása (a transzponálás során a sorokból
	 * oszlopok lesznek, az oszlopokból pedig sorok).
	 * 
	 * @param matrix a transzponálandó mátrix
	 * @return a transzponált mátrix
	 */
    public static int[][] transpose(int[][] matrix)
    {
        int row = getRowNum(matrix);	//mátrix sorainak száma
        int column = getColNum(matrix);	//mátrix oszlopainak száma

 		int[][] result = new int[column][row];

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < column; j++)
            {
                result[j][i] = matrix[i][j];
            }
        }

        return result;	//az eredmény mátrix
    }
    
    /**
     * A paraméterül kapott {@code int} mátrix másolása, hogy a továbbiakban ne az eredeti
     * {@code int} mátrix módosuljon (mellékhatás elkerülése).
     * 
     * @param matrix a másolandó mátrix
     * @return a másolat mátrix
     */
    public static int[][] copyMatrix(int[][] matrix)
    {
    	int row = getRowNum(matrix);	//mátrix sorainak száma
        int column = getColNum(matrix);	//mátrix oszlopainak száma

    	//új mátrix az eredeti alapján
    	int[][] newMatrix = new int[row][column];
    
    	for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < column; j++)
            {
                newMatrix[i][j] = matrix[i][j];	//másolás
            }
        }
    	return newMatrix;	//a másolat mátrix
    }

    /**
     * A paraméterül adott {@code int} mátrix sorainak a tükrözése (pl. 123-ból 321 lesz).
     * 
     * @param matrix a mátrix, amelyet tükrözök
     * @return új mátrix
     */
    public static int[][] reverseEachRow(int[][] matrix)
    {
    	//mátrix sorainak tükrözése
        int row = getRowNum(matrix);	//mátrix sorainak száma
        int column = getColNum(matrix);	//mátrix oszlopainak száma
        
        //innentől kezdve az új mátrixxal dolgozik
        //nincs mellékhatás, nem változtatom meg az eredetit
        int[][] newMatrix = copyMatrix(matrix);
        
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < (column / 2); j++)	//feléig megy
            {
                int tempHolder = newMatrix[i][j];
                newMatrix[i][j] =
                newMatrix[i][getColNum(newMatrix) - j - 1];
                //mert 0-ról indul az index
                newMatrix[i][getColNum(newMatrix) - j - 1] = tempHolder;
            }
        }
        return newMatrix;	//eredmény mátrix
    }
    
    /**
     * A megadott sor törlése a mátrixból. A megadott sor fölötti sorok rácsúsznak a sorra, és 
     * a legfelső sorban csupa 0 elemet tartalmazó sor jelenik.
     * 
     * @param matrix a mátrix
     * @param rowIndex az eltüntetendő sor indexe
     */
    public static void sweepRow(int[][] matrix, int rowIndex)
    {
    	int column = getColNum(matrix);
        if(rowIndex == 0)
        {
        	for (int j = 0; j < column; j++)
            {
        		matrix[0][j] = 0;	//speciális eset
            }
        }
        else
        {
            for (int i = rowIndex - 1; i >= 0; i--)
            {
                for (int j = 0; j < column; j++)
                {
                    //minden sort rámásolok a rákövetkezőre
                    matrix[i + 1][j] = matrix[i][j];
                    //0. sort kinullázom
                    if (i == 0)
                        matrix[i][j] = 0;
                }
            }
        }
    }
}