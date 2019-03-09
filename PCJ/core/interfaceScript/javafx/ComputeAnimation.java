package core.interfaceScript.javafx;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public final class ComputeAnimation {
	
	// Private constructor because we can't instantiate a "static" class
	private ComputeAnimation() {}
	
	// --- Start PathTransition
	/**
	 * 
	 * Method who create an path animation
	 * @param shape Shape who is follow by the object to animate
	 * @param node Object to animate
	 * @param time Duration (in second) to make 1 animation complete (1 cycle)
	 * @param cycle Number of cycle who be make by the object
	 * @return The PathAnimation who follow the parameters
	 */
	public static PathTransition makePathTransition(Shape shape, Node node, double time, int cycle) {
		PathTransition path = new PathTransition();
		path.setNode(node); // Improve -> make the same transition to many object (use Node... ?)
		path.setDuration(Duration.seconds(time));
		path.setPath(shape);
		path.setCycleCount(cycle);
		path.setInterpolator(Interpolator.LINEAR);
		return path;
	}
	
	/**
	 * 
	 * Method who create an path animation
	 * @param shape Shape who is follow by the object to animate
	 * @param node Object to animate
	 * @param time Duration (in second) to make 1 animation complete (1 cycle)
	 * @param cycle Number of cycle who be make by the object
	 * @param delay Duration (in second) before start the animation
	 * @return The PathAnimation who follow the parameters
	 */
	public static PathTransition makePathTransition(Shape shape, Node node, double time, int cycle, int delay) {
		PathTransition path = makePathTransition(shape, node, time, cycle);
		path.setDelay(Duration.seconds(delay));
		return path;
	}
	
	/**
	 * 
	 * Method who create an path animation and play the animation
	 * @param shape Shape who is follow by the object to animate
	 * @param node Object to animate
	 * @param time Duration (in second) to make 1 animation complete (1 cycle)
	 * @param cycle Number of cycle who be make by the object
	 */
	public static PathTransition playPathTransition(Shape shape, Node node, double time, int cycle) {
		PathTransition pt = makePathTransition(shape, node, time, cycle);
		pt.play();
		pt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pt.stop();				
			}
		});
		return pt;
	}
	// --- End PathTransition

	
	// --- Start FadeTransition
	/**
	 * 
	 * Method who create an fade transition who transit the object to his opposite opacity
	 * @param node Object to make a fade transition
	 * @param time Duration (in second) to make the transition
	 * @param cycle Number of cycle of transition
	 * @return The FadeTransition who follow the parameters
	 */
	public static FadeTransition makeFadeTransition(Node node, double time, int cycle) {
		FadeTransition fade = new FadeTransition();
		fade.setNode(node); // Improve -> make the same fade to many object (use Node... ?)
		fade.setDuration(Duration.seconds(time));
		fade.setFromValue(node.getOpacity());
		fade.setToValue(0.5 - (node.getOpacity() - 0.5)); // Formula : (max/2) - (value - (max/2) )
		fade.setCycleCount(cycle);
		return fade;
	}
	
	public static FadeTransition makeFadeTransition(Node node, double time, int cycle, double startOpacity, double endOpacity) {
		FadeTransition fade = new FadeTransition();
		fade.setNode(node); // Improve -> make the same fade to many object (use Node... ?)
		fade.setDuration(Duration.seconds(time));
		fade.setFromValue(startOpacity);
		fade.setToValue(endOpacity);
		fade.setCycleCount(cycle);
		return fade;
	}
	
	/**
	 * 
	 * Method who create an fade transition who transit the object to his opposite opacity
	 * @param node Object to make a fade transition
	 * @param time Duration (in second) to make the transition
	 * @param cycle Number of cycle of transition
	 */
	public static void playFadeTransition(Node node, double time, int cycle) {
		Animation ft = makeFadeTransition(node, time, cycle);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ft.stop();				
			}
		});
	}
	// --- End FadeTransition

	public static RotateTransition makeRotationTransition(Node n, double time, double angle, int cycle) {
		RotateTransition rt = new RotateTransition(Duration.seconds(time), n);
		rt.setByAngle(angle);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.setCycleCount(cycle);
		return rt;
	}
	
	public static RotateTransition playRotationTransition(Node n, double time, double angle, int cycle) {
		RotateTransition rt = makeRotationTransition(n, time, angle, cycle);
		rt.play();
		rt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rt.stop();				
			}
		});
		return rt;
	}
	
	public static Animation playTransitionAroundPoint(Shape s, Node n, double time, double angle, int cycle) {
		PathTransition pt = playPathTransition(s, n, time, cycle);
		RotateTransition rt = playRotationTransition(n, time, angle, cycle);
		return new ParallelTransition(pt, rt);
	}
	
	/**
	 * 
	 *  Method to make a flip like a card
	 * @param front 
	 * @param back
	 * @param duration
	 * @param cycle
	 * @param shader
	 * @return
	 */
	public static SequentialTransition makeFlip(Node front, Node back, double duration, int cycle, boolean shader) {
		SequentialTransition animation = new SequentialTransition( 
			flip(front, back, duration, shader)
	    ); 
	    animation.setCycleCount(cycle); 
	    return animation;
	}
	
	public static SequentialTransition makeFullFlip(Node front, Node back, double duration, int cycle, boolean shader) {
		SequentialTransition animation = new SequentialTransition( 
			flip(front, back, duration, shader),
			flip(back, front, duration, shader)
	    ); 
	    animation.setCycleCount(cycle); 
	    return animation;
	}
	
	public static void playFlip(Node front, Node back, double duration, int cycle, boolean shader) {
		SequentialTransition animation = makeFlip(front, back, duration, cycle, shader);
		animation.play();
		animation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				animation.stop();				
			}
		});
	}
	
	public static void playFlipAfterAnimation(Node front, Node back, double duration, int cycle, boolean shader, Animation firstAnimation) {
		SequentialTransition animation = makeFlip(front, back, duration, cycle, shader);
		animation.setDelay(firstAnimation.getCycleDuration());
		animation.setRate(2);
		animation.play();
		animation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				animation.stop();				
			}
		});
	}
	
	private static Transition flip(Node front, Node back, double duration, boolean shader) { 
		// ---------------
		// --- Modif for patch perspective camera
		// ---------------
		back.setVisible(false);
		// --- Start Flip
		Duration halfFlipDuration = Duration.seconds(duration / 2);
        RotateTransition rotateOutFront = new RotateTransition(halfFlipDuration, front); 
        rotateOutFront.setInterpolator(Interpolator.LINEAR); 
        rotateOutFront.setAxis(Rotate.Y_AXIS); 
        rotateOutFront.setFromAngle(0); 
        rotateOutFront.setToAngle(90); 
        // --------------
		// --- Modif for patch perspective camera
        // --------------
        rotateOutFront.setOnFinished(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent actEv) {
        		front.setVisible(false);
        		back.setVisible(true);
        	}
        });
        // -------------
        RotateTransition rotateInBack = new RotateTransition(halfFlipDuration, back); 
        rotateInBack.setInterpolator(Interpolator.LINEAR); 
        rotateInBack.setAxis(Rotate.Y_AXIS); 
        rotateInBack.setFromAngle(-90); 
        rotateInBack.setToAngle(0); 
        // --- End Flip
        ParallelTransition flipOutFront;
        ParallelTransition flipInBack;
        // --- Start Shader
        if (shader) {
        	ColorAdjust frontColorAdjust = new ColorAdjust();
    		ColorAdjust backColorAdjust = new ColorAdjust();
            Timeline changeBrightnessFront = new Timeline( 
            	new KeyFrame(Duration.ZERO, new KeyValue(frontColorAdjust.brightnessProperty(), 0)), 
                new KeyFrame(halfFlipDuration, new KeyValue(frontColorAdjust.brightnessProperty(), -1))
            ); 
            Timeline changeBrightnessBack = new Timeline( 
                new KeyFrame(Duration.ZERO, new KeyValue(backColorAdjust.brightnessProperty(), -1)), 
                new KeyFrame(halfFlipDuration, new KeyValue(backColorAdjust.brightnessProperty(), 0))
            ); 
            flipOutFront = new ParallelTransition(rotateOutFront, changeBrightnessFront); 
            flipInBack = new ParallelTransition(rotateInBack, changeBrightnessBack); 
        }
        // --- End Shader
        else {
        	flipOutFront = new ParallelTransition(rotateOutFront); 
        	flipInBack = new ParallelTransition(rotateInBack); 
        }
        return new SequentialTransition(flipOutFront, flipInBack); 
    }

}