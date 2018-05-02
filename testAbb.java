package Arboles;

public class testAbb {

	public static void main(String[] args) {
		//Creo un árbol con raíz 15 (Integer)
		
		Abb arbol= new Abb(15);
		arbol.insert(6);
		arbol.insert(18);
		arbol.insert(3);
		arbol.insert(7);
		arbol.insert(2);
		arbol.insert(4);
		arbol.insert(13);
		arbol.insert(9);
		arbol.insert(17);
		arbol.insert(20);
		
		arbol.printPreOrder();
		System.out.println("\n");
		

		System.out.println(arbol.hasElem(1));
		System.out.println(arbol.hasElem(18));


	}

}
