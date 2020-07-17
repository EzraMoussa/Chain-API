package com.edavalos.Crypto.Components;

import java.util.ArrayList;
import java.util.List;

public class Block<T> {
    private final int id;
    private byte[] proofHash;
    private final byte[] priorHash;
    private final List<Item<T>> items;
    private boolean sealed;

    public Block(int id, byte[] previousBlockHash) {
        this.id = id;
        priorHash = previousBlockHash;
        items = new ArrayList<>();
        sealed = false;
    }

    public void addItem(T item) {
        if (!sealed) {
            Item<T> newItem = new Item<>(items.size(), item);
            items.add(newItem);
        }
    }

    public byte[] getPriorHash() {
        return priorHash;
    }

    public byte[] getProofHash() {
        return sealed ? proofHash : null;
    }

    public List<Item<T>> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public void encrypt() {
        sealed = true;
        // do stuff
    }

/** #############################################
 *  # BLOCK ID: _id_                            #
 *  # PROOF HASH: _proofHash_ (might be null)   #
 *  # PRIOR HASH: _priorHash_                   #
 *  #                                           #
 *  # CONTENTS:                                 #
 *  # "transaction 1"                           #
 *  # "transaction 2"                           #
 *  # "transaction 3"                           #
 *  # "transaction 4"                           #
 *  # "transaction 5"                           #
 *  #############################################
 */

    @Override
    public String toString() {
        // do stuff
    }
}
