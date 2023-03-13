package com.cmput301w23t00.qrquest;

import org.junit.Test;

import static org.junit.Assert.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;

/**
 * Tests QR code processor methods
 */
public class QRCodeProcessorTest {
    @Test
    public void TestScoring() {
        QRCodeProcessor processor = new QRCodeProcessor("BFG5DGW54");
        assertEquals(processor.getScore(), 75);
    }

    @Test
    public void TestName() {
        QRCodeProcessor processor = new QRCodeProcessor("BFG5DGW54");
        assertEquals(processor.getName(), "hot FroMoMegaSpectralCrab");
    }

    @Test
    public void TestScoring2() {
        QRCodeProcessor processor2 = new QRCodeProcessor("BFG5DGW54\n");
        assertEquals(processor2.getScore(), 166);
    }

    @Test
    public void TestName2() {
        QRCodeProcessor processor = new QRCodeProcessor("BFG5DGW54\n");
        assertEquals(processor.getName(), "hot GloMoUltraSpectralCrab");
    }

    @Test
    public void TestScoring3() {
        QRCodeProcessor processor2 = new QRCodeProcessor("AshwinTest");
        assertEquals(processor2.getScore(), 66);
    }

    @Test
    public void TestName3() {
        QRCodeProcessor processor = new QRCodeProcessor("AshwinTest");
        assertEquals(processor.getName(), "hot FroMoMegaSonicCrab");
    }
}

