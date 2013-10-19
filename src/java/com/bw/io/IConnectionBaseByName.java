package com.bw.io;

import java.sql.Connection;

/**
 * Интерфейс соединения с базой по имени (получения и закрытия соединения).<br>
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public interface IConnectionBaseByName {

    /**
     * Получение соединения с базой
     *
     * @param sBaseName название базы
     * @return соединение
     * @throws Exception
     */
    public Connection oConnection(String sCase, String sBaseName) throws Exception;

    /**
     * Закрытие соединения с базой
     *
     * @param sBaseName название базы
     * @param oConnection соединение
     */
    public void closeConnection(String sCase, String sBaseName, Connection oConnection);
}