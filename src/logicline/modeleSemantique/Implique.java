package logicline.modeleSemantique;

import java.util.Set;

public class Implique extends Formule {
	
	Formule formuleGauche;
	Formule formuleDroite;
	
	public Implique(Formule gauche,Formule droite) {
		this.formuleGauche = gauche;
		this.formuleDroite = droite;
	}

	@Override
	public String toString() {
		return "(" + this.formuleGauche.toString() + ") ⇒ (" + this.formuleDroite.toString() + ")";
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
		return new Implique(this.formuleGauche.substitue(s),this.formuleDroite.substitue(s));
	}

	@Override
	public boolean valeur() throws VariableLibreException {
		if (this.variablesLibres().isEmpty()) {
			if (this.formuleGauche.valeur()) {
				if (this.formuleDroite.valeur()) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return true;
			}
		}
		else {
			throw new VariableLibreException("");
		}
	}
	
	@Override
	protected Formule supprImplications() {
		Formule gauche = new Non(this.formuleGauche.supprImplications());
		return new Ou(gauche,this.formuleDroite.supprImplications());
	}

}
