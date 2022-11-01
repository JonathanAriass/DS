package figuras;

import java.util.Stack;

public class Historial {

	private Stack<Cambio> undos = new Stack<Cambio>();
	private Stack<Cambio> redos = new Stack<Cambio>();
	
	public void addCambio(Cambio change) {
        undos.push(change);
        redos.clear();
    }

    public void undo() {
        if (undos.isEmpty()) {
            return;
        }
        Cambio change = undos.pop();
        change.undo();
        redos.push(change);
    }

    public void redo() {
        if (redos.isEmpty()) {
            return;
        }
        Cambio cambio = redos.pop();
        cambio.redo();
        undos.push(cambio);
    }
    
}
