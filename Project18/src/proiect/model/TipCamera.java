package proiect.model;

public enum TipCamera {

	SINGLE(1, 400, 1, 40), DOUBLE(2, 1000, 3, 100), TRIPLE(3, 1500, 8, 200), APARTAMENT(4, 2500, 12, 400);

	private final int nrLocuri;
	private final int pret;
	private final int intretinerePerZi;
	private final int incasarePerZi;

	private TipCamera(int n, int p, int intre, int incas) {
		nrLocuri = n;
		pret = p;
		intretinerePerZi = intre;
		incasarePerZi = incas;
	}

	public int getNrLocuri() {
		return nrLocuri;
	}

	public int getIntretinerePerZi() {
		return intretinerePerZi;
	}

	public int getIncasarePerZi() {
		return incasarePerZi;
	}

	public int getPret() {
		return pret;
	}

}
