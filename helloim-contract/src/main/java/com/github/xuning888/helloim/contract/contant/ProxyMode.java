package com.github.xuning888.helloim.contract.contant;


import java.io.Serializable;

/**
 * @author xuning
 * @date 2025/12/31 22:46
 */
public enum ProxyMode implements Serializable {

    /**
     * NAT
     */
    NAT("NAT"),
    ;

    private final String name;

    ProxyMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
