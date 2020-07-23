package com.edavalos.Crypto.Components;

import com.edavalos.Crypto.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.lang.reflect.Array;

public class Block<T> {
    private final int id;
    private final Date timestamp;
    private byte[] proofHash;
    private final byte[] priorHash;
    private final List<Item<T>> items;
    private boolean sealed;

    private T[] itemArray;

    public Block(int id, byte[] previousBlockHash) {
        this.id = id;
        timestamp = new Date();
        priorHash = previousBlockHash;
        items = new ArrayList<>();
        sealed = false;
    }

    public boolean addItem(T item) {
        if (sealed) return false;

        Item<T> newItem = new Item<>(items.size(), item);
        items.add(newItem);
        return true;
    }

    public byte[] getPriorHash() {
        return priorHash;
    }

    public byte[] getProofHash() {
        return sealed ? proofHash : null;
    }

    public T[] getItems() {
        if (itemArray == null || itemArray.length != items.size()) return null;

        for (int i = 0; i < items.size(); i++) {
            itemArray[i] = items.get(i).getContents();
        }
        return itemArray;
    }

    public int getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(T thing) {
        for (Item<T> item : items) {
            if (item.getContents().equals(thing)) return true;
        }
        return false;
    }

    public int size() {
        return items.size();
    }

    public void seal(Utility.HashTypes hash) {
        if (sealed) return;

        sealed = true;
        proofHash = Utility.byteHash(this.toString(), hash);
    }

/** BLOCK ID: _id_
 *  TIMESTAMP: _timestamp_
 *  PROOF HASH: _proofHash_ (might be null)
 *  PRIOR HASH: _priorHash_
 *
 *  CONTENTS:
 *   - "transaction 1" (0)
 *   - "transaction 2" (1)
 *   - "transaction 3" (2)
 *   - "transaction 4" (3)
 *   - "transaction 5" (4)
 */
    @Override
    public String toString() {
        String proof = (this.getProofHash() != null) ? Utility.bytesToHex(this.getProofHash()) : "[ Not created yet ]";
        String prior = Utility.bytesToHex(this.getPriorHash());

        StringBuilder items = new StringBuilder();
        for (Item<T> item : this.items) {
            items.append("\n - \"").append(item.getContents().toString()).append("\" (").append(item.getId()).append(")");
        }

        return "BLOCK ID: " + id + "\n" +
               "TIMESTAMP: " + timestamp.toString() + "\n" +
               "PRIOR HASH: " + prior + "\n" +
               "BLOCK HASH: " + proof + "\n" +
               "\n" +
               "CONTENTS:" + items.toString();
    }

    // updates the item array size
    public void resize(Class<T[]> clazz) {
        int len = items.size();
        itemArray = clazz.cast(Array.newInstance(clazz.getComponentType(), len));
    }
}
