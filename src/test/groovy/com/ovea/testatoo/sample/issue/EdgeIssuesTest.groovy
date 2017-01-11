package com.ovea.testatoo.sample.issue

import com.ovea.testatoo.sample.WebDriverConfig
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.testatoo.bundle.html5.list.MultiSelect
import org.testatoo.core.ComponentException
import org.testatoo.core.component.Item
import org.testatoo.core.component.ListBox

import static org.junit.Assert.fail
import static org.testatoo.core.Testatoo.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class EdgeIssuesTest {
    @ClassRule
    public static WebDriverConfig driver = new WebDriverConfig()

    @BeforeClass
    static void before() {
        visit 'http://localhost:8080/index.html'
    }

    @Test
    void should_have_expected_behaviours() {
        assert MultiSelect in ListBox

        ListBox cities = $('#cities') as MultiSelect

        assert cities.label() == 'Cities list'
        assert cities.items().size() == 6
        assert cities.visibleItems().size() == 3

        assert cities

        Item montreal = cities.item('Montreal')
        Item quebec = cities.item('Quebec')
        Item montpellier = cities.item('Montpellier')
        Item newYork = cities.item('New York')
        Item casablanca = cities.item('Casablanca')
        Item munich = cities.item('Munich')

        assert montreal.selected()
        assert montpellier.enabled()
        assert cities.item('Montreal').selected()

        assert !quebec.selected()
        assert !quebec.enabled()
        assert !cities.item('Quebec').selected()

        assert !montpellier.selected()
        assert !cities.item('Montpellier').selected()

        assert !newYork.selected()
        assert !cities.item('New York').selected()

        assert !casablanca.selected()
        assert !cities.item('Casablanca').selected()

        assert !munich.selected()
        assert !cities.item('Munich').selected()

        assert cities.selectedItems().containsAll(montreal)

        cities.unselect(montreal)
        cities.select(newYork, munich)

        assert cities.selectedItems().containsAll(newYork, munich)

        cities.select('Montpellier', 'Montreal')
        assert cities.item('Montpellier').selected()
        assert cities.item('Montreal').selected()
        assert cities.selectedItems().containsAll(newYork, munich, montpellier, montreal)

        montreal.unselect()
        montpellier.unselect()

        assert !cities.item('Montreal').selected()
        assert !cities.item('Montpellier').selected()
        assert cities.item('New York').selected()
        assert cities.item('Munich').selected()

        cities.select(montreal, montpellier)
        assert cities.item('Montreal').selected()
        assert cities.item('Montpellier').selected()
        assert cities.item('New York').selected()
        assert cities.item('Munich').selected()

        montpellier.click() // Now just Montpellier is selected
        assert montpellier.selected()
        assert !montreal.selected()
        assert !newYork.selected()
        assert !munich.selected()

        try {
            quebec.select()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Quebec is disabled and cannot be selected'
        }

        try {
            quebec.unselect()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Quebec is disabled and cannot be unselected'
        }

        try {
            newYork.unselect()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option New York is already unselected and cannot be unselected'
        }

        try {
            montpellier.select()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Montpellier is already selected and cannot be selected'
        }
    }
}
