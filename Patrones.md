## Strategy (comportamiento de objetos)
Define una familia de algoritmos, encapsula cada uno y los hace intercambiables. Permite que el algoritmo varié de forma independiente a los clientes que lo usan.

![Alt text](../img/Estrategy.png "Strategy")
![[Estrategy.png]]

```java
public interface PayStrategy {
	boolean pay(int amount);
	void collectPaymentDetails();
}
```

Ahora en cada clase que implemente la estrategia se redefinirán los diferentes métodos:
```java
public class PayByPaypal implements PayStrategy {
	// Se redefinen los metodos de la estrategia
	@Override
	public boolean pay(int amount) {}

	@Override
	public void collectPaymentDetails() {}
}
```

```java
public class PayByCreditCard implements PayStrategy {
	// Se redefinen los metodos de la estrategia
	@Override
	public boolean pay(int amount) {}

	@Override
	public void collectPaymentDetails() {}
}
```

Ejemplo de como usar las diferentes estrategias:
```java
public class Demo {
	// Se añaden productos a una lista para poder hacer operaciones (DummyData)

	public static void main(String[] args) {
		// Cosas previas no importantes
		if (strategy == null) {
                System.out.println("Please, select a payment method:" + "\n" +
                        "1 - PalPay" + "\n" +
                        "2 - Credit Card");
                String paymentMethod = reader.readLine();

                // Client creates different strategies based on input from user,
                // application configuration, etc.
                if (paymentMethod.equals("1")) {
                    strategy = new PayByPayPal();
                } else {
                    strategy = new PayByCreditCard();
                }
            }
	}
}
```
Aquí se puede ver como se puede hacer uso de una estrategia de cara a un usuario. Estas sentencias condicionales se podrían quitar con otro patrón pero para ver el caso de esto es mejor no quitarlas.
Otro ejemplo práctico sería el siguiente:
```java
public interface Validation {
	public boolean isValid(String texto);
}
```

```java
public class ValidateText implements Validation {

	@Override
	public boolean isValid(String texto) {}

}
```
Y así para todas las validaciones necesarias.

```java
public class Campo {

	private Validation val;

	public Campo(String text, Validation validation) {this.val = validation;}

	public void pideDato() {
		// Se piden datos mientras...
		do {
			//...
			text = consola.readLine();
		} while (val.isValid(text));
		
	}

}
```

```java
public class Formulario {

	private List<Campo> campos = new ArrayList<Campo>();

	public void addCampo(Campo campo) {
		campos.add(campo);
	}

	public void PideDatos() {
		for (Campo c : campos) {
			c.pideDato();
		}
	}

}
```

```java
public class Demo {

	public static void main(String[] args) {
		Formulario formulario = new Formulario();

		// Se pueden añadir las validaciones (Strategy) aqui mismo
		formulario.addCampo(new Campo("Nombre", new CheckText()));
		formulario.addCampo(new Campo("Apellido", new CheckText()));
		formulario.addCampo(new Campo("Tel�fono", new CheckNumber()));
		formulario.addCampo(new Campo("Ciudad",

		new CheckValues("Santander", "Oviedo", "C�diz")));

		formulario.PideDatos();
	}

}
```

### Consecuencias
Se eliminan multiples condicionales.
Define familias de algoritmos relacionados.
Es una alternativa a la herencia.

Crecerá el número de objetos y se puede complicar la comunicación entre el contexto y las estrategias.




## Composite (estructurales de objetos)
Permite componer objetos en estructuras arbóreas para representar jerarquías de todo-parte, de modo que los clientes puedan tratar a los objetos individuales y a los compuestos de manera uniforme.

![[Composite.png]]

```java
public interface Shape {
	int getX(); int getY(); int getWidth(); int getHeight();
	void move();
	boolean isInBounds(int x, int y);
	...
	void paint(Graphics graphics);
}
```

Se puede usar una clase abstracta en mitad para definir ciertos aspectos base a todas las figuras, pero en este ejemplo es irrelevante.

```java
public class Circle implements Shape() {
	// Se hará override a todos los metodos
	@Override
	public void paint(Graphics graph) {...}
}
```

```java
public class Dot implements Shape() {
	// Se hará override a todos los metodos
	@Override
	public void paint(Graphics graph) {...}
}
```

```java
public class Rectangle implements Shape() {
	// Se hará override a todos los metodos
	@Override
	public void paint(Graphics graph) {...}
}
```

Aquí viene el uso del Composite:
```java
public class CompoundShape implements Shape() {

	// Aqui se esta haciendo uso del patron composite
	private List<Shape> children = new ArrayList<>();

	// Tendrá metodos para añadir a la lista
	public void add(Shape comp) {children.add(comp);}
	public void remove(Shape comp) {children.remove(comp);}

	// Se hará override a todos los metodos
	@Override
	public void paint(Graphics graph) {
		// Dentro de este metodo se pintara cada uno de los hijos que esten
		// dentro de la lista <<children>>
	}
}
```

Y cuando se haga una estructura en árbol el CompoundShape se comportará de la misma forma que otra figura, ya que los métodos son comunes a todas las figuras, lo que único que cambian son las implementaciones.




## STATE (comportamiento de objetos)
Permite a un objeto alterar su comportamiento cuando cambia su estado interno. Parecerá como si el objeto hubiera cambiado su clase.

![[State.png]]

```java
public abstract class State {

	public Player player;

	State(Player player) {this.player = player;}

	public abstract String onLock();
	public abstract String onPlay();
	public abstract String onNext();
	public abstract String onPrevious();

}
```

```java
public class LockedState extends State {

	public LockedState(Player player) {
		super(player);
	}

	// Se hace override de cada metodo del state para controlar que puede hacer cada 
    // state
    // En el metodo onPlay() se podra cambiar el estado a ReadyState()
	@Override
    public String onPlay() {
        player.changeState(new ReadyState(player));
        return "Ready";
    }
}
```
Se hará lo mismo para todos los estados que existan y cada estado hará su función.

```java
public class Player {

	private State state;

	public Player() {
		this.state = new ReadyState(this);
		setPlaying(true);
	}

	// Metodos extras del reproductor
}
```

```java
public class UI {

	private Player player;

	public UI(Player player) {
		this.player = player;
	}

	public void init() {
		// Se ajustan cosas de la aplicacion
		JButton play = new JButton("Play");
        play.addActionListener(e -> textField.setText(player.getState().onPlay()));
        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> textField.setText(player.getState().onLock()));
        JButton next = new JButton("Next");
        next.addActionListener(e -> textField.setText(player.getState().onNext()));
        JButton prev = new JButton("Prev");
        prev.addActionListener(e -> textField.setText(player.getState().onPrevious()));
	}

}
```

```java
public class Demo {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
```
Con esto son los propios estados los que se manejan a si mismos.




## Template method (comportamiento de clases)
Define el esqueleto de un algoritmo en una operación, difiriendo algunos pasos hasta las subclases. Permite que éstas redefinan ciertos pasos del algoritmo sin cambiar la estructura del algoritmo en sí.

![[Template.png]]

```java
public abstract class Network {

	Network() {}

	public boolean post(String message) {
		// Se realiza el logIn y se manda el mensaje
	}

	abstract boolean logIn(String userName, String password);
	abstract boolean sendData(byte[] data);
	abstract void logOut();

}
```

Ahora se implementaran las diferentes redes sociales y los metodos abstractos de Network:
```java
public class Facebook extends Network {

	public Facebook(String username, String password) {...}

	public boolean logIn(String username, String password) {
		// Se realizara el login con la api de Facebook
	}

	public boolean sendData(byte[] data) {
		// Se mandará la informacion por los endPoints de Facebook
	}

	public void logOut() {
		// Se realizara con al info de Facebook
	}

}
```
Esto mismo se tendría que hacer para todas las redes sociales que soporte la aplicación.

```java
public class Demo {

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Network network = null;
        System.out.print("Input user name: ");
        String userName = reader.readLine();
        System.out.print("Input password: ");
        String password = reader.readLine();

        // Enter the message.
        System.out.print("Input message: ");
        String message = reader.readLine();

        System.out.println("\nChoose social network for posting message.\n" +
                "1 - Facebook\n" +
                "2 - Twitter");
        int choice = Integer.parseInt(reader.readLine());

        // Create proper network object and send the message.
        if (choice == 1) {
            network = new Facebook(userName, password);
        } else if (choice == 2) {
            network = new Twitter(userName, password);
        }
        network.post(message);
	}

}
```
Con esto conseguimos que la forma de mandar el post sea la misma para todos haciendo uso de los templates de las redes sociales.

Los template methods podran llamar a los siguientes metodos:
* Operaciones de otras clases
* Operaciones de la propia clase abstracta (se pueden redefinir en las subclases si hace falta)
* Operaciones primitivas (abstractas que se redefinen si o si)
* Metodos de fabricacion
* Operaciones Hook (normalmente protegidas con implementaciones vacias en la clase abstracta que se podrá redefinir en las subclases si hace falta)




## Adapter (estructural de clases y objetos)
Convierte la interfaz de una clase en otra que es la que esperan los clientes. Permite que trabajen juntas clases que de otro modo no podrían por tener interfaces incompatibles.

#### Adapter de clases
![[Adapter(Clases).png]]
Se hace uso de la herencia multiple para poder usar una interfaz para un objeto que no tiene nada en común con esta.

#### Adapter de objetos
![[Adapter(Objetos).png]]
En este caso lo que se hace es adaptar el objeto a la interfaz Target.





## Command (comportamiento de objetos)
Encapsula una petición dentro de un objeto, permitiendo parametrizar a los clientes con distintas peticiones, encolarlas, guardarlas en un registro de sucesos o implementar un mecanismo de undo/redo.

![[Command.png]]

### Ejemplo
![[EjemploCommand.png]]

```java
public class EditorTextUI {

	private Editor editor;

	public void run() {
		// Se checkea que instruccion se necesita con if y bufferedReader
		if (line[0].equals("abre")) {
			editor.open(line[1]); // Se llama al editor
		}
		...
	}

}
```

```java
public class Editor {

	private ActionManager manager = new ActionManager(this);

	// Habra un metodo por operacion que llame al actionManager.execute()

	public void open(String filename) {
		manager.execute(new Open(filename));
	}
	
}
```

```java
public class ActionManager {

	private Editor editor;

	public void execute(Action action) {
		action.execute(editor);
	}

}
```

```java
public interface Action {

	void execute(Editor editor);

}
```

Un ejemplo de action:
```java
public class Open implements Action {

	// Variables de clase

	@Override
	public void execute(Editor editor) {
		// Metodo que le da valor al texto del editor
		editor.setText(readFile(filename)); 
	}

}
```
En las action se realizaran acciones que afecten al editor.

Con este patron resulta sencillo añadir nuevas acciones, al no tener que tocar las clases existentes y además se desacopla el objeto que llama a la operación del que sabe cómo llevarla a cabo.




## Decorator (estructural de objetos)
Añade responsabilidades adicionales a un objeto dinámicamente. Los decoradores proporcionan una alternativa flexible a la herencia para extender la funcionalidad.

![[Decorator.png]]

Este sería el componente (interfaz):
```java
public interface DataSource {

	void writeData(String data);
	String readData();

}
```

```java
public class FileDataSource implements DataSource {

	...

	@Override
	public void writeData(String data) {...}

	@Override
	public String readData() {...}


}
```

```java
public class DataSourceDecorator implements DataSource {

	private DataSource wrapee;

	DataSourceDecorator(DataSource source) {this.wrapee = source;}

	@Override
	public void writeData(String data) {
		wrappee.writeData(data);
	}

	@Override
	public String readData() {
		return wrappee.readData();
	}

}
```

Ahora implementaremos los decoradores concretos:
```java
public class EncryptionDecorator extends DataSourceDecorator {

	public EncryptionDecorator(DataSource source) {
		super(source);
	}

	@Override
	public void writeData(String data) {
		super.writeData(encode(data));
	}

	private String encode(String data) {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] += (byte) 1;
        }
        return Base64.getEncoder().encodeToString(result);
    }

	// Se haria lo mismo para readData
	...
}
```

El otro decorator concreto tendria otra funcionalidad pero tiene el mismo fundamento. Este seria el de añadir una funcionalidad extra el metodo base de la clase decorator.

```java
public class Demo {

	public static void main(String[] args) {
        String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";
        DataSourceDecorator encoded = new CompressionDecorator(
                                         new EncryptionDecorator(
                                             new FileDataSource("out/OutputDemo.txt")));
        encoded.writeData(salaryRecords);
        DataSource plain = new FileDataSource("out/OutputDemo.txt");

        System.out.println("- Input ----------------");
        System.out.println(salaryRecords);
        System.out.println("- Encoded --------------");
        System.out.println(plain.readData());
        System.out.println("- Decoded --------------");
        System.out.println(encoded.readData());
    }

}
```
Se puede ver como en la segunda linea del main se crea un DataSourceDecorator el cual:
* Crea un objeto de lectura/escritura en fichero dentro de un objeto de encriptacion
* Y el objeto de encriptacion esta dentro del de compresion
Una vez se llame al encoded.writeData el orden de ejecucion sera el siguiente:
	compresion --> encriptacion --> escritura en fichero
Esto es gracias a la concatenacion de las llamadas al writeData con diferentes metodos dentro de cada ConcreteDecorator.

Esto dara mas flexibilidad que la herencia estatica y evita que las clases de arriba de la jerarquia esten repletas de funcionalidades. Pero un decorador y sus componentes no son identicos y ademas habra muchos objetos pequeños creando un sistema complejo de aprender y depurar.
