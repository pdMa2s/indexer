package documentReader;

import org.xml.sax.helpers.DefaultHandler;

/**
 * An abstract implementation of a {@link DefaultHandler}, adds complexity to this class by forcing the users that use
 * this class to implement methods that are useful for processing the documents of the sample corpus.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 *
 */
public abstract class DefaultCorpusXMLHandler extends DefaultHandler {
    /**
     * A function that returns a {@link String} containing the text content of a document.
     * @return The text of the document.
     */
    public abstract String getText();

    /**
     * Returns the ID of the document that as been processed.
     * @return The ID of the document.
     */
    public abstract int getID();
}
