package core.interfaceScript.javafx.skin;
 
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
 
public class CloudSkin implements Skin<Button> {
 
    Button control;
    String text = "";
 
    Group rootNode = new Group();
    Label lbl = null;
    //int direction = MyBtnInterface.RIGHT;
    EventHandler retourClient = null;
    double labelWidth;
    double labelHeight;
 
    String normalStyle="-fx-text-fill:#FFFFFF;";
    String activeStyle="-fx-text-fill:#FF0000;";
    Color beginColor=Color.LIGHTBLUE;
    Color middleColor=Color.ALICEBLUE;
    Color endColor=Color.LIGHTBLUE;
 
 
    /*******************************
     * Constructeur
     * affecte le control par defaut
     * dessine le controle
     * @param control
     *******************************/
    public CloudSkin(Button control) {
        this.control = control;
        labelWidth = control.getPrefWidth();
        labelHeight = control.getPrefHeight();
	    lbl = new Label(control.getText());
	    lbl.setStyle(control.getStyle());
//	    control.setStyle("-fx-background-color: transparent;");
        control.setPickOnBounds(false);
        redessine();
    }
 
 
	/****************************
	 * Retourne l'objet Node du noeud en cours
	 * ne doit jamais être null
	 * @return
	 ****************************/
    @Override
    public Node getNode() {
	    if (rootNode == null) {
		    rootNode = new Group();
		    redessine();
		}
		return this.rootNode;
	}

    @Override
    public void dispose() {
    	// nettoie les ressources 
    	// getSkinnable() et getNode() ne devront donc plus rien retourner
    }
 
	 
	/********************
	 * Dessiner le bouton
	 ********************/
	public void redessine() {
	 
		//position de translation par rapport au layout
		lbl.setTranslateX(labelWidth / 2);
		lbl.setTranslateY(labelHeight / 4);
		 
		// Creer le path des elements du bouton
		Path path = new Path();
		MoveTo startPoint = new MoveTo();
		 
		// --- Draw Cloud
		startPoint.setX(labelWidth * 0.1);
		startPoint.setY(labelHeight * 0.175);
		
		QuadCurveTo quadCurveTo = new QuadCurveTo();
		quadCurveTo.setX(labelWidth * 0.375);
		quadCurveTo.setY(0);
		quadCurveTo.setControlX(labelWidth * 0.25);
		quadCurveTo.setControlY(- (labelHeight * 0.125));
		
		QuadCurveTo quadCurveTo2 = new QuadCurveTo();
		quadCurveTo2.setX(0.75 * labelWidth);
		quadCurveTo2.setY(0);
		quadCurveTo2.setControlX(labelWidth* 0.675);
		quadCurveTo2.setControlY(- (labelHeight * 0.125));
		
		QuadCurveTo quadCurveTo3 = new QuadCurveTo();
		quadCurveTo3.setX(labelWidth);
		quadCurveTo3.setY(0.5 * labelHeight);
		quadCurveTo3.setControlX(labelWidth * 1.125);
		quadCurveTo3.setControlY(labelHeight * 0.125);
		
		QuadCurveTo quadCurveTo4 = new QuadCurveTo();
		quadCurveTo4.setX(0.875 * labelWidth);
		quadCurveTo4.setY(labelHeight * 0.75);
		quadCurveTo4.setControlX(labelWidth);
		quadCurveTo4.setControlY(labelHeight * 0.75);
		
		QuadCurveTo quadCurveTo5 = new QuadCurveTo();
		quadCurveTo5.setX(labelWidth * 0.5);
		quadCurveTo5.setY(labelHeight * 0.675);
		quadCurveTo5.setControlX(labelWidth * 0.6);
		quadCurveTo5.setControlY(labelHeight * 0.875);
		
		QuadCurveTo quadCurveTo6 = new QuadCurveTo();
		quadCurveTo6.setX(labelWidth * 0.175);
		quadCurveTo6.setY(labelHeight * 0.575);
		quadCurveTo6.setControlX(labelWidth * 0.25);
		quadCurveTo6.setControlY(labelHeight * 0.8);
		
		QuadCurveTo quadCurveTo7 = new QuadCurveTo();
		quadCurveTo7.setX(labelWidth * 0.1);
		quadCurveTo7.setY(labelHeight * 0.175);
		quadCurveTo7.setControlX(- (labelWidth * 0.025));
		quadCurveTo7.setControlY(labelHeight * 0.375);
		
		
		path.getElements().add(startPoint);
		path.getElements().add(quadCurveTo);
		path.getElements().add(quadCurveTo2);
		path.getElements().add(quadCurveTo3);
		path.getElements().add(quadCurveTo4);
		path.getElements().add(quadCurveTo5);
		path.getElements().add(quadCurveTo6);
		path.getElements().add(quadCurveTo7);
		control.setShape(path);
		 
		// Creer un jeu de gradient dans le bouton
		Stop[] stops = new Stop[] {
		    new Stop(0.0, beginColor),
		    new Stop(0.5, middleColor),
		    new Stop(1.0,endColor)
		};
		 
		LinearGradient lg =new LinearGradient( 0, 0, 1, 1, true, CycleMethod.REFLECT, stops);
		path.setFill(lg);
		 
		//ajouter les éléments au noeud principal (Group)
		rootNode.getChildren().setAll(path, lbl);
		 
		//affecter la capture du clic souris sur ce bouton
//		rootNode.setOnMouseClicked((MouseEvent mc) -> {
//		    if ( retourClient != null ) retourClient.handle(mc);
//		    control.getOnMouseClicked();
//			});
//		 
//		 
//		rootNode.setOnMouseEntered((MouseEvent me) -> {
//		    if ( retourClient != null ) retourClient.handle(me);
//		    lbl.setStyle(activeStyle);
//		    });
//		 
//		rootNode.setOnMouseExited((MouseEvent mr) -> {
//		    if ( retourClient != null ) retourClient.handle(mr);
//		    lbl.setStyle(normalStyle);
//		    });
		}
	 
	 
	/**************************
	 * Affecter le titre du btn
	 * @param text
	 ***************************/
    public void setText(String text) {
        	this.text = text;
	        lbl.setText(text);
	        lbl.setPrefSize(labelWidth, labelHeight);
	        lbl.setAlignment(Pos.CENTER);
	 
	        // update button
	        redessine();
	    }
	 
	    /********************
	     * Affecter la taille du btn
	     * @param X
	     * @param Y
	     *******************/
	    public void setSize(double X, double Y){
	        labelWidth=X;
	        labelHeight=Y;
	        redessine();
	    }
	 
	 
	    public void setActiveStyle(String style){
	        activeStyle=style;
	    }
	 
	 
	    public void setNormalStyle(String style){
	        normalStyle=style;
	    }
	 
	    public void setBeginColor(Color couleur){
	        beginColor=couleur;
	    }
	 
	    public void setMiddleColor(Color couleur){
	        middleColor=couleur;
	    }
	 
	      public void setEndColor(Color couleur){
	        endColor=couleur;
	    }
	 
	/*************************
	 * Retourne le controle
	 * ici l'objet button
	 * @return
	 *************************/
	@Override
	public Button getSkinnable() {
	    return this.control;
	}
 
}