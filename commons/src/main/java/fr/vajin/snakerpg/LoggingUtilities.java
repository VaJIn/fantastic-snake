package fr.vajin.snakerpg;

import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;

public abstract class LoggingUtilities {

    private static boolean printAllData = false;

    private static String buildPacketString(DatagramPacket packet, String header) {
        StringBuilder debugMessageBuilder =
                new StringBuilder(header)
                        .append(System.lineSeparator())
                        .append("\t")
                        .append("Address : ")
                        .append(packet.getAddress())
                        .append(":")
                        .append(packet.getPort())
                        .append(System.lineSeparator());
        if (printAllData) {
            byte[] data = packet.getData();
            for (int i = 0; i < packet.getLength(); i++) {
                if (i != 0 && i % 32 == 0) {
                    debugMessageBuilder.append(System.lineSeparator()).append("\t");
                } else if (i != 0 && i % 4 == 0) {
                    debugMessageBuilder.append("\t");
                }
                debugMessageBuilder.append(String.format("%02X ", data[i]));
            }
        }
        return debugMessageBuilder.toString();
    }

    public static void logPacketDebug(Logger logger, DatagramPacket packet, String header) {
        if (logger.isDebugEnabled()) {
            logger.debug(buildPacketString(packet, header));
        }
    }

}
