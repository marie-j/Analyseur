package logicline.modeleSemantique;

import java.util.Set;

public class Variable extends Formule {
	
	String variable;
	
	public Variable (String v) {
		super();
		this.variable =v;
	}

	@Override
	public String toString() {
		return this.variable ;
	}

	@Override
	public Set<String> variablesLibres() {
		this.libres.add(this.variable);
		return libres;
	}

	@Override
	public Formule substitue(Substitution s) {
		Formule f = s.get(this.variable);
		if (f != null) {
			return f;
		}
		else {
			return this;
		}
	}

	@Override
	public boolean valeur() throws VariableLibreException {
		throw new VariableLibreException(this.variable);
	}
	
	@Override
	public ListeClauses clauses() throws VariableClauseException {
		throw new VariableClauseException(this.variable);
	}
	
	//méthode qui retourne la clause obtenue 
	public Clause clause() throws TrueClauseException, FalseClauseException {
		return new Clause(this.variable,true);
	}

}
