package logicline.modeleSemantique;

import java.util.Set;

public class Constante extends Formule {
	
	 Boolean  constante;
	
	public Constante (Boolean constante) {
		this.constante = constante;
	}

	@Override
	public String toString() {
		if (this.constante) {
			return "(T)";
		}
		else {
			return "(⊥)";
		}
	}

	@Override
	public Set<String> variablesLibres() {
		return null;
	}

	@Override
	public Formule substitue(Substitution s) {
		return this;
	}

	@Override
	public boolean valeur() throws VariableLibreException {
		return this.constante;
	}
	
	@Override
	public ListeClauses clauses() throws TrueClauseException, FalseClauseException {
		if (this.constante) {
			throw new TrueClauseException();
		}
		else {
			throw new FalseClauseException();
		}
	}
	
	//méthode qui retourne la clause obtenue 
	public Clause clause() throws TrueClauseException, FalseClauseException {
		if (this.constante) {
			throw new TrueClauseException();
		}
		else {
			throw new FalseClauseException();
		}
	}
}
