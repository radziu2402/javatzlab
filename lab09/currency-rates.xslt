<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="utf-8" indent="yes"/>
    <xsl:template match="/">
        <html>
            <body>
                <h1>Kursy walut</h1>
                <table border="1" style="border-collapse: collapse; width: 100%;">
                    <tr style="background-color: #f2f2f2;">
                        <th>Waluta Bazowa</th>
                        <th>Waluta Docelowa</th>
                        <th>Kurs Wymiany</th>
                    </tr>
                    <xsl:for-each select="channel/item">
                        <tr>
                            <td>
                                <xsl:value-of select="baseCurrency"/>
                            </td>
                            <td>
                                <xsl:value-of select="targetCurrency"/>
                            </td>
                            <td>
                                <xsl:value-of select="exchangeRate"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
