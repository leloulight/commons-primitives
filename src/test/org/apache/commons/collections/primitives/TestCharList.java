/*
 * Copyright 2002-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections.primitives;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.apache.commons.collections.primitives.adapters.BaseTestList;
import org.apache.commons.collections.primitives.adapters.CharListList;
import org.apache.commons.collections.primitives.adapters.ListCharList;

/**
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff
 */
public abstract class TestCharList extends BaseTestList {

    // conventional
    // ------------------------------------------------------------------------

    public TestCharList(String testName) {
        super(testName);
    }

    // collections testing framework
    // ------------------------------------------------------------------------

    // collections testing framework: char list
    // ------------------------------------------------------------------------

    protected abstract CharList makeEmptyCharList();

    protected CharList makeFullCharList() {
        CharList list = makeEmptyCharList();
        char[] values = getFullChars();
        for(int i=0;i<values.length;i++) {
            list.add(values[i]);
        }
        return list;
    }

    protected char[] getFullChars() {
        char[] result = new char[19];
        for(int i = 0; i < result.length; i++) {
            result[i] = (char)(i);
        }
        return result;
    }

    protected char[] getOtherChars() {
        char[] result = new char[16];
        for(int i = 0; i < result.length; i++) {
            result[i] = (char)(i + 43);
        }
        return result;
    }
    
    // collections testing framework: inherited
    // ------------------------------------------------------------------------

    public List makeEmptyList() {
        return new CharListList(makeEmptyCharList());
    }
        
    public Object[] getFullElements() {
        return wrapArray(getFullChars());
    }

    public Object[] getOtherElements() {
        return wrapArray(getOtherChars());
    }

    // private utils
    // ------------------------------------------------------------------------

    private Character[] wrapArray(char[] primitives) {
        Character[] result = new Character[primitives.length];
        for(int i=0;i<result.length;i++) {
            result[i] = new Character(primitives[i]);            
        }
        return result;
    }

    // tests
    // ------------------------------------------------------------------------

    public void testExceptionOnConcurrentModification() {
        CharList list = makeFullCharList();
        CharIterator iter = list.iterator();
        iter.next();
        list.add((char)3);
        try {
            iter.next();
            fail("Expected ConcurrentModificationException");
        } catch(ConcurrentModificationException e) {
            // expected
        }
    }
    
    public void testAddAllCharListAtIndex() {
        CharList source = makeFullCharList();
        CharList dest = makeFullCharList();
        dest.addAll(1,source);
        
        CharIterator iter = dest.iterator();
        assertTrue(iter.hasNext());
        assertEquals(source.get(0),iter.next());
        for(int i=0;i<source.size();i++) {
            assertTrue(iter.hasNext());
            assertEquals(source.get(i),iter.next());
        }
        for(int i=1;i<source.size();i++) {
            assertTrue(iter.hasNext());
            assertEquals(source.get(i),iter.next());
        }
        assertFalse(iter.hasNext());
    }

    public void testToJustBigEnoughCharArray() {
        CharList list = makeFullCharList();
        char[] dest = new char[list.size()];
        assertSame(dest,list.toArray(dest));
        int i=0;
        for(CharIterator iter = list.iterator(); iter.hasNext();i++) {
            assertEquals(iter.next(),dest[i], 0f);
        }
    }
    
    public void testToLargerThanNeededCharArray() {
        CharList list = makeFullCharList();
        char[] dest = new char[list.size()*2];
        for(int i=0;i<dest.length;i++) {
            dest[i] = Character.MAX_VALUE;
        }       
        assertSame(dest,list.toArray(dest));
        int i=0;
        for(CharIterator iter = list.iterator(); iter.hasNext();i++) {
            assertEquals(iter.next(),dest[i]);
        }
        for(;i<dest.length;i++) {
            assertEquals(Character.MAX_VALUE,dest[i]);
        }
    }
    
    public void testToSmallerThanNeededCharArray() {
        CharList list = makeFullCharList();
        char[] dest = new char[list.size()/2];
        char[] dest2 = list.toArray(dest);
        assertTrue(dest != dest2);
        int i=0;
        for(CharIterator iter = list.iterator(); iter.hasNext();i++) {
            assertEquals(iter.next(),dest2[i], 0f);
        }
    }
    
    public void testHashCodeSpecification() {
        CharList list = makeFullCharList();
        int hash = 1;
        for(CharIterator iter = list.iterator(); iter.hasNext(); ) {
            hash = 31*hash + ((int)iter.next());
        }
        assertEquals(hash,list.hashCode());
    }

    public void testEqualsWithTwoCharLists() {
        CharList one = makeEmptyCharList();
        assertEquals("Equals is reflexive on empty list",one,one);
        CharList two = makeEmptyCharList();
        assertEquals("Empty lists are equal",one,two);
        assertEquals("Equals is symmetric on empty lists",two,one);
        
        one.add((char)1);
        assertEquals("Equals is reflexive on non empty list",one,one);
        assertTrue(!one.equals(two));
        assertTrue(!two.equals(one));

        two.add((char)1);
        assertEquals("Non empty lists are equal",one,two);
        assertEquals("Equals is symmetric on non empty list",one,two);
        
        one.add((char)1); one.add((char)2); one.add((char)3); one.add((char)5); one.add((char)8);
        assertEquals("Equals is reflexive on larger non empty list",one,one);
        assertTrue(!one.equals(two));
        assertTrue(!two.equals(one));
        
        two.add((char)1); two.add((char)2); two.add((char)3); two.add((char)5); two.add((char)8);
        assertEquals("Larger non empty lists are equal",one,two);
        assertEquals("Equals is symmetric on larger non empty list",two,one);

        one.add((char)9);
        two.add((char)10);
        assertTrue(!one.equals(two));
        assertTrue(!two.equals(one));

    }

    public void testCharSubListEquals() {
        CharList one = makeEmptyCharList();
        assertEquals(one,one.subList(0,0));
        assertEquals(one.subList(0,0),one);
        
        one.add((char)1);
        assertEquals(one,one.subList(0,1));
        assertEquals(one.subList(0,1),one);

        one.add((char)1); one.add((char)2); one.add((char)3); one.add((char)5); one.add((char)8);
        assertEquals(one.subList(0,4),one.subList(0,4));
        assertEquals(one.subList(3,5),one.subList(3,5));
    }
    
    public void testEqualsWithCharListAndList() {
        CharList ilist = makeEmptyCharList();
        List list = new ArrayList();
        
        assertTrue("Unwrapped, empty List should not be equal to empty CharList.",!ilist.equals(list));
        assertTrue("Unwrapped, empty CharList should not be equal to empty List.",!list.equals(ilist));
        
        assertEquals(new ListCharList(list),ilist);
        assertEquals(ilist,new ListCharList(list));
        assertEquals(new CharListList(ilist),list);
        assertEquals(list,new CharListList(ilist));
        
        ilist.add((char)1);
        list.add(new Character((char)1));

        assertTrue("Unwrapped, non-empty List is not equal to non-empty CharList.",!ilist.equals(list));
        assertTrue("Unwrapped, non-empty CharList is not equal to non-empty List.",!list.equals(ilist));
        
        assertEquals(new ListCharList(list),ilist);
        assertEquals(ilist,new ListCharList(list));
        assertEquals(new CharListList(ilist),list);
        assertEquals(list,new CharListList(ilist));
                
        ilist.add((char)1); ilist.add((char)2); ilist.add((char)3); ilist.add((char)5); ilist.add((char)8);
        list.add(new Character((char)1)); list.add(new Character((char)2)); list.add(new Character((char)3)); list.add(new Character((char)5)); list.add(new Character((char)8));

        assertTrue("Unwrapped, non-empty List is not equal to non-empty CharList.",!ilist.equals(list));
        assertTrue("Unwrapped, non-empty CharList is not equal to non-empty List.",!list.equals(ilist));
        
        assertEquals(new ListCharList(list),ilist);
        assertEquals(ilist,new ListCharList(list));
        assertEquals(new CharListList(ilist),list);
        assertEquals(list,new CharListList(ilist));
        
    }

    public void testClearAndSize() {
        CharList list = makeEmptyCharList();
        assertEquals(0, list.size());
        for(int i = 0; i < 100; i++) {
            list.add((char)i);
        }
        assertEquals(100, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    public void testRemoveViaSubList() {
        CharList list = makeEmptyCharList();
        for(int i = 0; i < 100; i++) {
            list.add((char)i);
        }
        CharList sub = list.subList(25,75);
        assertEquals(50,sub.size());
        for(int i = 0; i < 50; i++) {
            assertEquals(100-i,list.size());
            assertEquals(50-i,sub.size());
            assertEquals((char)(25+i),sub.removeElementAt(0), 0f);
            assertEquals(50-i-1,sub.size());
            assertEquals(100-i-1,list.size());
        }
        assertEquals(0,sub.size());
        assertEquals(50,list.size());        
    }
    
    public void testAddGet() {
        CharList list = makeEmptyCharList();
        for (int i = 0; i < 255; i++) {
            list.add((char)i);
        }
        for (int i = 0; i < 255; i++) {
            assertEquals((char)i, list.get(i), 0f);
        }
    }

    public void testAddAndShift() {
        CharList list = makeEmptyCharList();
        list.add(0, (char)1);
        assertEquals("Should have one entry", 1, list.size());
        list.add((char)3);
        list.add((char)4);
        list.add(1, (char)2);
        for(int i = 0; i < 4; i++) {
            assertEquals("Should get entry back", (char)(i + 1), list.get(i), 0f);
        }
        list.add(0, (char)0);
        for (int i = 0; i < 5; i++) {
            assertEquals("Should get entry back", (char)i, list.get(i), 0f);
        }
    }

    public void testIsSerializable() throws Exception {
        CharList list = makeFullCharList();
        assertTrue(list instanceof Serializable);
        byte[] ser = writeExternalFormToBytes((Serializable)list);
        CharList deser = (CharList)(readExternalFormFromBytes(ser));
        assertEquals(list,deser);
        assertEquals(deser,list);
    }

    public void testCharListSerializeDeserializeThenCompare() throws Exception {
        CharList list = makeFullCharList();
        if(list instanceof Serializable) {
            byte[] ser = writeExternalFormToBytes((Serializable)list);
            CharList deser = (CharList)(readExternalFormFromBytes(ser));
            assertEquals("obj != deserialize(serialize(obj))",list,deser);
        }
    }

    public void testSubListsAreNotSerializable() throws Exception {
        CharList list = makeFullCharList().subList(2,3);
        assertTrue( ! (list instanceof Serializable) );
    }

    public void testSubListOutOfBounds() throws Exception {
        try {
            makeEmptyCharList().subList(2,3);
            fail("Expected IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // expected
        }

        try {
            makeFullCharList().subList(-1,3);
            fail("Expected IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // expected
        }


        try {
            makeFullCharList().subList(5,2);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }

        try {
            makeFullCharList().subList(2,makeFullCharList().size()+2);
            fail("Expected IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // expected
        }
    }

    public void testListIteratorOutOfBounds() throws Exception {
        try {
            makeEmptyCharList().listIterator(2);
            fail("Expected IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // expected
        }

        try {
            makeFullCharList().listIterator(-1);
            fail("Expected IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // expected
        }

        try {
            makeFullCharList().listIterator(makeFullCharList().size()+2);
            fail("Expected IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            // expected
        }
    }

    public void testListIteratorSetWithoutNext() throws Exception {
        CharListIterator iter = makeFullCharList().listIterator();
        try {
            iter.set((char)3);
            fail("Expected IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }

    public void testListIteratorSetAfterRemove() throws Exception {
        CharListIterator iter = makeFullCharList().listIterator();
        iter.next();
        iter.remove();
        try {            
            iter.set((char)3);
            fail("Expected IllegalStateException");
        } catch(IllegalStateException e) {
            // expected
        }
    }

    public void testCharListToString() throws Exception {
        String expected = "The quick brown fox jumped over the lazy dogs.";
        CharList list = makeEmptyCharList();
        for(int i=0;i<expected.length();i++) {
            list.add(expected.charAt(i));
        }
        assertEquals(expected,list.toString());
    }
}
