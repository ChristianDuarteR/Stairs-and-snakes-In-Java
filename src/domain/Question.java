package domain;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
public class Question extends Box{
	
	private JFrame shouldanswer;
	
	private JLabel etiqueta;
	
	private JComboBox<String> preguntona;
	
	private JButton ok;

    private HashMap<String,String[]> MyQuest;

    private ArrayList<Integer> respuestas;
    
    private boolean res;
    
    private String opcion;
    
    /**
     * Constructor de la clase Question.
     * @param tbl el tablero asociado a la pregunta.
     */
    public Question(Tablero tbl) {
        super(tbl);
        MyQuest = new HashMap<>();
        respuestas = new ArrayList<>();
        setMyQuest();
    }
    
    /**
     * Establece las preguntas y respuestas para el juego.
     * Las preguntas y respuestas se almacenan en el mapa MyQuest y en la lista respuestas, respectivamente.
     * Cada pregunta se representa como una cadena de texto, seguida de un array de opciones de respuesta.
     * La respuesta correcta se indica mediante el índice de la opción en el array de respuestas.
     * Las preguntas y respuestas son predefinidas en este método.
     */
    private void setMyQuest(){
        String question1 = "Cual es la capital de Australia?";
        String[] posRespuestas1 = {"a) Sydney ", "b) Melbourne ","c) Brisbane","d) Canberra ","e) Perth "};

        MyQuest.put(question1,posRespuestas1);
        respuestas.add(0);

        String question2 = "Cual es el oceano mas grande de el mundo?";
        String[] posRespuestas2 = {"a) Atlantico ", "b) Indico ","c) Pacifico","d) Artico ","e) Antartico "};

        MyQuest.put(question2,posRespuestas2);
        respuestas.add(1);

        String question3 = "Quien es el autor de la novela 'Cien años de soledad'";
        String[] posRespuestas3 = {"a) Gabriel Garcia Marquez ", "b) Mario Vargas Llosa ","c) Pablo Neruda",
                "d) Octavio Paz ","e) Julio Cortazar "};

        MyQuest.put(question3,posRespuestas3);
        respuestas.add(2);

        String question4 = "En que pais se encuentra la torre Eiffel?";
        String[] posRespuestas4 = {"a) España ", "b) Francia ","c) Italia","d) Alemania ","e) Inglaterra "};

        MyQuest.put(question4,posRespuestas4);
        respuestas.add(3);

        String question5 = "Cual es el elemento quimico mas abundante en la corteza terrestre";
        String[] posRespuestas5 = {"a) Hidrogeno ", "b) Oxigeno ","c) Hierro","d) Aluminio ","e) Calcio "};

        MyQuest.put(question5,posRespuestas5);
        respuestas.add(4);
    }

    /**
     * Verifica si el objeto tiene un poder especial.
     * @return true si el objeto tiene un poder especial, false de lo contrario.
     */
    @Override
    public boolean hasApower(){
        return true;
    }
    
    /**
     * Mueve el token de la ficha con poder especial y muestra una pregunta al jugador.
     * @param ficha la ficha que se moverá con poder especial.
     * @throws StairsAndSnakesException si ocurre algún error durante el movimiento.
     */
    @Override
    public void moveTokenWithPower(Ficha ficha) throws StairsAndSnakesException {
        super.moveTokenWithPower(ficha);
        JOptionPane.showMessageDialog(null,"Preparese para responder una pregunta!!");
        ficha.canMove = false;
        ficha.setMaxCas(getValue());
    }

    /**
     * Realiza una pregunta al jugador y habilita la posibilidad de mover la ficha.
     * @param ficha la ficha del jugador que debe responder la pregunta.
     * @return  true si se realizó la pregunta correctamente y se habilitó el movimiento de la ficha, false en caso contrario.
     */
    
    public boolean doQuestion(Ficha ficha) throws StairsAndSnakesException{
    	shouldanswer = new JFrame();
    	shouldanswer.setLayout(null);
        shouldanswer.setSize(350, 220);
        shouldanswer.setLocationRelativeTo(null);
    	etiqueta = null;
    	preguntona = null;
    	
    	ok = new JButton("Listo");
		opcion = (JOptionPane.showInputDialog(
				"Que pregunta quiere?\n"
				+ "1\n" 
				+ "2\n" 
				+ "3\n" 
				+ "4\n" 
				+ "5\n"));                                                                                                     
		if (opcion.equals("") || opcion == null) throw new StairsAndSnakesException(StairsAndSnakesException.NOT_ALLOW_NUMBER);
		switch(Integer.parseInt(opcion)) {
    		case 1:
    			preguntona = new JComboBox<>(MyQuest.get("Cual es la capital de Australia?"));
    			etiqueta = new JLabel("Cual es la capital de Australia?");
    			break;
    		case 2:
    			preguntona = new JComboBox<>(MyQuest.get("Cual es el oceano mas grande de el mundo?"));
    			etiqueta = new JLabel("Cual es el oceano mas grande de el mundo?");
    			break;
    		case 3:
    			preguntona = new JComboBox<>(MyQuest.get("Quien es el autor de la novela 'Cien años de soledad'"));
    			etiqueta = new JLabel("Quien es el autor de la novela 'Cien años de soledad'");
    			break;
    		case 4:
    			preguntona = new JComboBox<>(MyQuest.get("En que pais se encuentra la torre Eiffel?"));
    			etiqueta = new JLabel("En que pais se encuentra la torre Eiffel?");
    			break;
    		case 5:
    			preguntona = new JComboBox<>(MyQuest.get("Cual es el elemento quimico mas abundante en la corteza terrestre"));
    			etiqueta = new JLabel("Cual es el elemento quimico mas abundante en la corteza terrestre");
    			break;
    			default:
    				JOptionPane.showMessageDialog(null, "Opcion invalida, escoja una pregunta");
    				boolean question = doQuestion(ficha);
    				return question;

       		}
    	preguntona.setBounds(80,50,100,20);
    	ok.setBounds(100,100,90,20);
    	etiqueta.setBounds(10,150,250,20);
		shouldanswer.add(etiqueta);
		shouldanswer.add(preguntona);
		shouldanswer.add(ok);
	    shouldanswer.setVisible(true);
	    ok.addActionListener(e -> getItems());
	    return res;
    }
    
    private void getItems() {
    	String respuesta =  (String) preguntona.getSelectedItem();
    	int index = Integer.parseInt(opcion);
    	res = verifyAnswer(respuesta,index);
    	shouldanswer.dispose();
    	etiqueta.setText("");
    	

    }
    
    private boolean verifyAnswer(String res, int index) {
    	Integer correcta = respuestas.get(index);
    	switch (correcta) {
    	case 1:
    		System.out.print(res + "\n");
    		return (res.equals(MyQuest.get("Cual es la capital de Australia?")[3]));
    	case 2:
    		System.out.print(res + "\n");
    		return (res.equals(MyQuest.get("Cual es el oceano mas grande de el mundo?")[2]));
    	case 3:
    		System.out.print(res + "\n");
    		return (res.equals(MyQuest.get("Quien es el autor de la novela 'Cien años de soledad'")[0]));
    	case 4:
    		System.out.print(res + "\n");
    		System.out.print(MyQuest.get("En que pais se encuentra la torre Eiffel?")[1]);
    		return (res.equals(MyQuest.get("En que pais se encuentra la torre Eiffel?")[1]));
    	case 5:
    		System.out.print(res + "\n");
    		return (res.equals(MyQuest.get("Cual es el elemento quimico mas abundante en la corteza terrestre")[3]));
    	default:
    		return false;
    	}
    }

}
