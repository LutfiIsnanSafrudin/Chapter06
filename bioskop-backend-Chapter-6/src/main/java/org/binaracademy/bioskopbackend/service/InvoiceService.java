package org.binaracademy.bioskopbackend.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface InvoiceService {

    byte[] generateInvoice(String username) throws FileNotFoundException, JRException;

}
