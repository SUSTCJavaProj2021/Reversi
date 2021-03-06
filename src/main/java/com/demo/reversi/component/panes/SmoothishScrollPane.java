package com.demo.reversi.component.panes;

import com.demo.reversi.themes.Theme;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * ScrollPane with kinda smooth transition scrolling.
 *
 * @author Matt
 *
 * Speeded up the scrolling process and added some styles.
 * The original author is Matt.
 */
public class SmoothishScrollPane extends ScrollPane {
    private final static int TRANSITION_DURATION = 200;
    private final static double BASE_MODIFIER = 4; //Changed from 1 to 6

    /**
     * @param content Item to be wrapped in the ScrollPane.
     */
    public SmoothishScrollPane(Node content) {
        // ease-of-access for inner class
        ScrollPane scroll = this;

        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setFitToWidth(true);

        // set content in a wrapper
        VBox wrapper = new VBox(content);
        setContent(wrapper);

        //Load transparent stage style.
        try {
            getStylesheets().add(Theme.class.getResource("ScrollPane.css").toExternalForm());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            getStylesheets().add(Theme.class.getResource("ScrollBar.css").toExternalForm());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        // add scroll handling to wrapper
        wrapper.setOnScroll(new EventHandler<ScrollEvent>() {
            private SmoothishTransition transition;

            @Override
            public void handle(ScrollEvent event) {
                double deltaY = BASE_MODIFIER * event.getDeltaY() ;
                double width = scroll.getContent().getBoundsInLocal().getWidth();
                double vvalue = scroll.getVvalue();
                Interpolator interpolator = Interpolator.LINEAR;
                transition = new SmoothishTransition(transition, deltaY) {
                    @Override
                    protected void interpolate(double frac) {
                        double x = interpolator.interpolate(vvalue, vvalue + -deltaY * getMod() / width, frac);
                        scroll.setVvalue(x);
                    }
                };
                transition.play();
            }
        });

        //finally
        setPannable(true);
    }

    /**
     * @param t Transition to check.
     * @return {@code true} if transition is playing.
     */
    private static boolean playing(Transition t) {
        return t.getStatus() == Status.RUNNING;
    }

    /**
     * @param d1 Value 1
     * @param d2 Value 2.
     * @return {@code true} if values signs are matching.
     */
    private static boolean sameSign(double d1, double d2) {
        return (d1 > 0 && d2 > 0) || (d1 < 0 && d2 < 0);
    }

    /**
     * Transition with varying speed based on previously existing transitions.
     *
     * @author Matt
     */
    abstract class SmoothishTransition extends Transition {
        private final double mod;
        private final double delta;

        public SmoothishTransition(SmoothishTransition old, double delta) {
            setCycleDuration(Duration.millis(TRANSITION_DURATION));
            setCycleCount(0);
            // if the last transition was moving inthe same direction, and is still playing
            // then increment the modifer. This will boost the distance, thus looking faster
            // and seemingly consecutive.
            if (old != null && sameSign(delta, old.delta) && playing(old)) {
                mod = old.getMod() + 1;
            } else {
                mod = 1;
            }
            this.delta = delta;
        }

        public double getMod() {
            return mod;
        }

        @Override
        public void play() {
            super.play();
            // Even with a linear interpolation, startup is visibly slower than the middle.
            // So skip a small bit of the animation to keep up with the speed of prior
            // animation. The value of 10 works and isn't noticeable unless you really pay
            // close attention. This works best on linear but also is decent for others.
            if (getMod() > 1) {
                jumpTo(getCycleDuration().divide(10));
            }
        }
    }
}
