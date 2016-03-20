package logicline.modeleSemantique;

import java.util.Set;

public class Ou extends Formule {
	
	Formule formuleGauche;
	Formule formuleDroite;

	public Ou(Formule gauche,Formule droite) {
		this.formuleGauche = gauche;
		this.formuleDroite = droite;
	}
	
	@Override
	public String toString() {
		return "(" + this.formuleGauche.toString() + ") ∨ (" + this.formuleDroite.toString() + ")";
	}

	@Override
	public Set<String> variablesLibres() {
		Set<String> libresGauche = formuleGauche.variablesLibres();
		Set<String> libresDroite = formuleDroite.variablesLibres();
		this.libres.addAll(libresGauche);
		this.libres.addAll(libresDroite);
		return this.libres;
	}

	@Override
	public Formule substitue(Substitution s) {
		return new Ou(this.formuleGauche.substitue(s),this.formuleDroite.substitue(s));
	}

	@Override
	public boolean valeur() throws VariableLibreException {
		if (this.variablesLibres().isEmpty()) {
			return this.formuleGauche.valeur() || this.formuleDroite.valeur();
		}
		else {
			throw new VariableLibreException("");
		}
	}
	
	@Override
	protected Formule negation() {
		return new Et(this.formuleGauche.negation(),this.formuleDroite.negation());
	}
	
	@Override 
	protected Formule entrerDisjonctions() {
		
		if (this.formuleGauche.contientEt()) {
			
			Formule fg = ((Et) this.formuleGauche).formuleGauche;
			Formule fd = ((Et) this.formuleDroite).formuleDroite;
			
			return new Et(fg.ougauche(this.formuleDroite), fd.ougauche(this.formuleDroite));
			
		}
		
		else {
			return this.formuleGauche.ougauche(this.formuleDroite);
		}
	}
	
	@Override
	public ListeClauses clauses() throws NotFNCException, TrueClauseException, FalseClauseException, VariableClauseException {
		
		ListeClauses clauses = new ListeClauses();
		Clause clause = new Clause();
		
		if (this.formuleGauche instanceof Ou) {
			
			Formule fg = ((Ou) this.formuleGauche).formuleGauche;
			Formule fd = ((Ou) this.formuleGauche).formuleDroite;
			
			clauses.addAll(fg.clauses());
			clauses.addAll(fd.clauses());
		}
		
		else {
			
			Clause c = this.formuleGauche.clause();
			clause.addAll(c);		
		}
		
		if (this.formuleDroite instanceof Ou) {
			
			Formule fg = ((Ou) this.formuleDroite).formuleGauche;
			Formule fd = ((Ou) this.formuleDroite).formuleDroite;
			
			clauses.addAll(fg.clauses());
			clauses.addAll(fd.clauses());
		}
		
		else {
			
			Clause c = this.formuleDroite.clause();
			clause.addAll(c);		
		}
		
		clauses.add(clause);
		return clauses;
	}
	
}
