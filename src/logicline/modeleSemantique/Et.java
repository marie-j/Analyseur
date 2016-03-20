package logicline.modeleSemantique;

import java.util.Set;

public class Et extends Formule {
	
	Formule formuleGauche;
	Formule formuleDroite;
	
	public Et(Formule gauche,Formule droite) {
		super();
		this.formuleGauche = gauche;
		this.formuleDroite = droite;
	}

	@Override
	public String toString() {
		return "(" + this.formuleGauche.toString() + ") ∧ (" + this.formuleDroite.toString() + ")";
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
		return new Et(this.formuleGauche.substitue(s),this.formuleDroite.substitue(s));
	}

	@Override
	public boolean valeur() throws VariableLibreException {
		if (this.variablesLibres().isEmpty()) {
			return this.formuleGauche.valeur() && this.formuleDroite.valeur();
		}
		else {
			throw new VariableLibreException("");
		}
	}
	
	@Override
	protected Formule negation() {
		return new Ou(this.formuleGauche.negation(),this.formuleDroite.negation());
	}
	
	@Override 
	protected boolean contientEt() {
		return true;
	}
	
}
