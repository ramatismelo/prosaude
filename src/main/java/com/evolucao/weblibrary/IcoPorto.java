package com.evolucao.weblibrary;

import com.vaadin.server.FontIcon;

/**
 * Enum that matches the content of our font icon (this example font has
 * only one icon).
 */
public enum IcoPorto implements FontIcon {
    COMPARE(0xe867), 
	MINICART(0Xe863),
	ICONSEARCH(0Xe812),
	ARROWDOWN(0Xe81c),
	HEARTH(0Xe811),
	CHART(0Xe810),
	PREVIEWARROW(0xe816),
	NEXTARROW(0Xe817),
	ICONRIGHTDIR(0xe814),
	EMAIL(0Xe825),
	ICONLOCATION(0Xe826),
	CLOCK(0Xe838),
	TELEPHONE(0Xe80a),
	TRIANGLEDOWN(0Xe80b),
	ICONOK(0Xe84e),
	CART(0Xe80c),
	GRID(0Xe80e),
	LIST(0Xe80f),
	LUPA(0Xe003),
	REMOVE(0Xe82c),
	ARROWUP(0Xe820),
	STAR(0Xe852);

    // You can see the codepoints in the IcoMoon app, or in the demo.html
    private final int codepoint;
    // This must match (S)CSS
    private final String fontFamily = "porto";

    IcoPorto(int codepoint) {
        this.codepoint = codepoint;
    }

    @Override
    public String getFontFamily() {
        return fontFamily;
    }

    @Override
    public int getCodepoint() {
        return codepoint;
    }

    @Override
    public String getHtml() {
        return "<span class=\"v-icon porto\">&#x"+ Integer.toHexString(codepoint) + ";</span>";
    }

    @Override
    public String getMIMEType() {
        // Font icons are not real resources
        throw new UnsupportedOperationException(
                FontIcon.class.getSimpleName()
                        + " should not be used where a MIME type is needed.");
    }
}