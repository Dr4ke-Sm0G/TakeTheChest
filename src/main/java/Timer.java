package main.java;

public class Timer {
	
	private int remaining_ticks;
	private boolean finished;
	
	public Timer(int seconds) {
		this.setRemainingTicks(seconds*60);
		this.setFinished(false);
	}

	public int getRemainingTicks() {
		return remaining_ticks;
	}

	public void setRemainingTicks(int seconds) {
		this.remaining_ticks = seconds;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	@Override
	public String toString() {
		return "Temps restant : "+String.valueOf((int) (this.getRemainingTicks()/60))+" secondes.";
	}

	/**
     * Met a jour le timer.
     */
    public void tick() {
        if(this.remaining_ticks <= 0) this.setFinished(true);
        else this.setRemainingTicks(this.getRemainingTicks()-1);
    }
}
