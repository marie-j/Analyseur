package logicline.modeleSemantique;

import java.util.Set;

public class Non extends Formule {
	
	protected Formule formule;
	
	public Non(Formule f) {
		this.formule = f;
	}
	
	@Override
	public String toString() {
		return "¬ (" + this.formule.toString() + ")";
	}

	@Override
	public Set<String> variablesLibres() {
		this.libres.addAll(this.formule.variablesLibres());
		return this.libres;
	}

	@Override
	public Formule substitue(Substitution s) {
		return new Non(this.formule.substitue(s));
	}

	@Override
	public boolean valeur() throws VariableLibreException {
		return  !this.formule.valeur();
	}
	
	@Override
	protected Formule negation() {
		return this.formule;
	}
	
	@Override
	public ListeClauses clauses() throws VariableClauseException {
		throw new VariableClauseException(this.negation().toString());
	}
	
	//méthode qui retourne la clause obtenue 
	public Clause clause() throws TrueClauseException, FalseClauseException {
		return new Clause(this.negation().toString(),false);
	}

}
