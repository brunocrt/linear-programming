package linear;

//import org.json.JSONObject;

public class Simplex {

    private static final int COLUNA_ML = 1;
    private static final int LINHA_FO = 1;
    private double[][] celulaSuperior;
    private double[][] celulaInferior;
    private boolean[][] marcados;
    private int colunaPermitida;
    private int linhaPermitida;
    private int linhamembroLivre;
    private static final int CURR_BEGIN  = 1 ;

    /**
     * Metodo que executa primeira parte do Algoritmo Simplex
     */
    private void primeiroPasso() {
        linhamembroLivre = getMembroLivre();
        while(linhamembroLivre!=Integer.MAX_VALUE){
            colunaPermitida = getVnbNegativa(linhamembroLivre);
            if(colunaPermitida!=Integer.MAX_VALUE){
                resolverIteracao();
                linhamembroLivre = getMembroLivre();
            }else{
                System.out.println("Não existe solução para este problema!");
                break;
            }
        }
    }

    /**
    * Metodo que executa segunda parte do Algoritmo Simplex
    */
   private void segundoPasso(){
       colunaPermitida = getColunaElementoPositivoFO();
       while(colunaPermitida != Integer.MAX_VALUE){
         if(!verificaSeIlimitado()){
           resolverIteracao();
           colunaPermitida = getColunaElementoPositivoFO();
         }else{
           System.out.println("Este problema possui solução ilimitada!!!");
           break;
         }
       }
   }


    /**
     * Funcao para encontra membro livre negativo
     * @return Retorna o indice do membro livre negativo na matriz  caso ele exista
     */
    private int getMembroLivre() {
        for (int linha = CURR_BEGIN + 1; linha < celulaSuperior.length; linha++) {
            if (celulaSuperior[linha][0] < 0) {
                return linha;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Funcao que procura por um VNB Negativa na linha do ML negativo
     * @param linha Linha do membro livre negativo
     * @return Retorna o indice da VNB  negativa na linha do ML
     */
    private int getVnbNegativa(int linha) {
        for (int coluna = CURR_BEGIN + 1; coluna < celulaSuperior[0].length ;coluna++) {
            if (celulaSuperior[linha][coluna] < 0) {
                return coluna;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Funcao que retorna a linha permitida com menor razao entro o ML e o
     * elemento da coluna permitida
     * @return Retorna o indice da linha permitida caso seja possivel
     */
    private int getLinhaElementoPermitido() {
        return getLinhaMenor();
    }

    /**
     * Metodo que multiplica a celula superior  da linha do elemento permitido
     * pelo inverso do elemento permitido e armazena na celula Infrior
     * Tambem marca os elementos da linha do elemento permitido
     * */
    private void multiplicaLinha(double inverso) {
        for (int coluna = CURR_BEGIN; coluna < celulaSuperior[0].length; coluna++) {
            //Se celulaSuperior  = 0, apenas coloque 0 ma celula inferior,
            //pois e possivel que se multiplicarmos por um valor negativo
            //obtenhamos o valor -0
            celulaInferior[linhaPermitida][coluna] = (celulaSuperior[linhaPermitida][coluna] == 0) ? 0 : celulaSuperior[linhaPermitida][coluna] * (inverso);
            marcados[linhaPermitida][coluna] = true;
        }
    }

    /**
     * Metodo que multiplica a celula superior  da coluna do elemento permitido
     * pelo inverso multiplicado por  -1 do elemento permitido e armazena na celula Inferior
     * Tambem marca os elementos da coluna do elemento permitido
     * @param inverso Inverso do elemento selecionado
     */
    private void multiplicaColuna(double inverso) {
        for (int linha = CURR_BEGIN; linha < celulaSuperior.length; linha++) {
            //Se celulaSuperior  = 0, apenas coloque 0 ma celula inferior,
            //pois e possivel que se multiplicarmos por um valor negativo
            //obtenhamos o valor -0
            celulaInferior[linha][colunaPermitida] = (celulaSuperior[linha][colunaPermitida] == 0) ? 0 : celulaSuperior[linha][colunaPermitida] * ((-1) * inverso);
            marcados[linha][colunaPermitida] = true;
        }
    }

    /**
     * Funcao que retorna o indice da linha do elemento com menor razao positiva
     * ML/Elemento da coluna permitida  caso exista
     * @return Indice da linha do elemento de menor razao
     */
    private int getLinhaMenor() {
        double menor = Double.MAX_VALUE;
        int linhaMenor = Integer.MAX_VALUE;
        double razao;
        for (int linha = CURR_BEGIN + 1; linha < celulaSuperior.length; linha++) {
            //Evita divisoes por 0
            if (celulaSuperior[linha][colunaPermitida] != 0) {
                //ML/Elemento da Coluna Permitida
                razao = (celulaSuperior[linha][COLUNA_ML] / celulaSuperior[linha][colunaPermitida]);
                if (razao > 0 && razao < menor) {
                    menor = razao;
                    linhaMenor = linha;
                }
            }
        }
        return linhaMenor;
    }

    /**
     * Metodo que mostra a matriz do Simplex
     */
    private void mostrar() {
        System.out.println("===============================");
        for (int x = 0; x < celulaSuperior.length ; x++) {
            System.out.print("|\n");
            for (int y = 0; y < celulaSuperior[0].length; y++) {
                System.out.print("(" + celulaSuperior[x][y] + "/" + celulaInferior[x][y] + ")");
            }
            System.out.print("|\n");
        }
        System.out.println("===============================");
    }

    /**
     * Metodo que mostra os elementos marcados
     */
    private void mostrarMarcados() {
        for (int x = 0; x < marcados.length ; x++) {
            for (int y = 0; y < marcados[0].length ; y++) {
                System.out.print("(" + marcados[x][y] + ") ");
            }
            System.out.print("\n");
        }
    }

    /**
    *Metodo que realiza uma iterecao do algoritmo Simplex
    */

    private void resolverIteracao(){
      linhaPermitida = getLinhaElementoPermitido();
      double inverso = (1 / celulaSuperior[linhaPermitida][colunaPermitida]);
      multiplicaLinha(inverso);
      multiplicaColuna(inverso);
      //mostrarMarcados();
      celulaInferior[linhaPermitida][colunaPermitida] = inverso;
      algoritmoDaTroca();
    }


     /**
     * Funcao que  procura indice da coluna de um elemento positivo na linha da funcao objetiva
     *@return Indice da coluna do elemento positivo
     */
     private int getColunaElementoPositivoFO(){
      for(int coluna = CURR_BEGIN + 1 ;  coluna < celulaSuperior[0].length ; coluna ++){
        if(celulaSuperior[LINHA_FO][coluna] > 0){
          return coluna;
        }
      }
      return Integer.MAX_VALUE;
    }
   /**
     * Funcao que  procura indice da coluna de um elemento positivo na linha da funcao objetiva
     *@return coluna Indice da coluna do elemento positivo
     **/
    private boolean  verificaSeIlimitado(){
      for(int linha   =  CURR_BEGIN + 1 ; linha < celulaSuperior.length; linha ++ ){
        if(celulaSuperior[linha][colunaPermitida] > 0){
            return false;
        }
      }
      return true;
    }


    /**
    * Metodo que preenche a celula inferior dos elementos nao marcados com
    * o produto do elemento marcado da coluna com o da linha
    */
    private void preencheCelulaInferior(int linhaPermitida, int colunaPermitida) {
        for (int linha = CURR_BEGIN; linha < celulaInferior.length; linha++) {
            for (int coluna = CURR_BEGIN; coluna < celulaInferior[0].length; coluna++) {
                if (!marcados[linha][coluna]) {
                    celulaInferior[linha][coluna] = getMarcadoLinha(linha) * getMarcadoColuna(coluna);
                }
            }
        }
    }

    /**
    *Funcao que busca o elemento marcado de uma linha
    *@param linha Linha que deseja proucurar
    *@return elemento marcado da linha
    */
    private double getMarcadoLinha(int linha) {
        for (int coluna = CURR_BEGIN; coluna < celulaInferior.length; coluna++) {
            if (marcados[linha][coluna]) {
                return celulaInferior[linha][coluna];
            }
        }
        return Double.MAX_VALUE;
    }
    /**
    *Funcao que busca o elemento marcado de uma coluna
    *@param coluna Coluna que deseja proucurar
    *@return elemento marcado da coluna
    */
    private double getMarcadoColuna(int coluna) {
        for (int linha = CURR_BEGIN; linha < celulaSuperior.length; linha++) {
            if (marcados[linha][coluna]) {
                return celulaSuperior[linha][coluna];
            }
        }
        return Double.MAX_VALUE;
    }

    /**
    *Metodo que soma valores da celula inferior com o da
    *celula superior dos elementos nao marcados
    */
    private void somaValores() {
        for (int linha = CURR_BEGIN; linha < celulaSuperior.length; linha++) {
            for (int coluna = CURR_BEGIN; coluna < celulaSuperior[0].length; coluna++) {
                if(!marcados[linha][coluna]){
                    celulaSuperior[linha][coluna]+= celulaInferior[linha][coluna];
                }
            }
        }
    }
    /**
    *Metodo que realiza  o algoritmo da troca
    */
    private void algoritmoDaTroca() {
        preencheCelulaInferior(linhaPermitida, colunaPermitida);
        mostrar();
        trocaValores();
        somaValores();
        celulaInferior = new double[celulaSuperior.length][celulaSuperior[0].length];
        marcados = new boolean[celulaSuperior.length][celulaSuperior[0].length];
        mostrar();
    }

    /**
    *Metodo troca valores da linha e coluna permitidas passando os
    * os valores das celulas inferiores para as superiores
    */
    private void trocaValores() {
		double aux = celulaSuperior[linhaPermitida][0];
		celulaSuperior[linhaPermitida][0] = celulaSuperior[0][colunaPermitida];
		celulaSuperior[0][colunaPermitida] = aux;
        trocaValoresLinha();
        trocaValoresColuna();
    }
    /**
    *Metodo troca valores da linha permitida passando os
    * os valores das celulas inferiores para as superiores
    */
    private void trocaValoresLinha() {
        int aux = 0;
        for (int coluna = CURR_BEGIN; coluna < celulaSuperior[0].length; coluna++) {
			celulaSuperior[linhaPermitida][coluna] = celulaInferior[linhaPermitida][coluna];
        }
    }
    /**
    *Metodo troca valores da coluna permitida passando os
    * os valores das celulas inferiores para as superiores
    */
    private void trocaValoresColuna() {
        int aux = 0;
        for (int linha = CURR_BEGIN; linha < celulaSuperior.length; linha++) {
            if (linha != linhaPermitida) {
					celulaSuperior[linha][colunaPermitida] = celulaInferior[linha][colunaPermitida];
            }
        }
    }

    /**
     * Metodo que executa teste do algoritmo
     */
    public  void teste() {
        double[] variaveis = {5.0, 3.5};
        double[][] restricoes
                =
                {{0, 5.0, 3.5},
                {400, 1.5, 1.0},
                {150, 1.0, 0.0},
                {300, 0.0, 1.0}};

        //double[][] matriz
                //=
                //{{0, 14.0, 22},
                //{250, 2.0, 4.0},
                //{-460, -5.0, -8.0},
                //{40, 1.0, 0.0}};
        double[] vars = {1.0,2.0};				
		double[][] matriz
                =
                {
				{-1.0,-1.0  ,  1.0,  2.0},
				{-1.0, 0.0  , 80.0, 60.0},
                { 3.0, 24.0,  -4.0,  6.0},
                { 4.0,16.0, 4.0, 2.0},
                { 5.0,  3.0,  0.0,  1.0}};
				
				
        celulaInferior = new double[matriz.length][matriz[0].length];	 
		marcados = new boolean[matriz.length][matriz[0].length];
        this.celulaSuperior = matriz.clone();
        primeiroPasso();
        segundoPasso();
    }


    /**
    *Metodo que  interpreta funcao objetiva
    */
    private void processarFuncao(){

    } 
	
	public void solve(double[][] matriz){
		celulaInferior = new double[matriz.length][matriz[0].length];	 
		marcados = new boolean[matriz.length][matriz[0].length];
        this.celulaSuperior = matriz.clone();
        primeiroPasso();
        segundoPasso();
	}
	
	
	public  void branchAndBound(double[][] matriz){
		//solve(matriz);
		
	}


    public static void main(String[] args){
      new Simplex().teste();
    }
    /**
    *Metodo que resolve o Simplex
    */
    //public JSONObject solve(){
    //  processarFuncao();
    //  primeiroPasso();
    //  segundoPasso();
    //  return new JSONObject();
    //}
}

