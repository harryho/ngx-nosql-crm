import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Category e2e test', () => {

    let navBarPage: NavBarPage;
    let categoryDialogPage: CategoryDialogPage;
    let categoryComponentsPage: CategoryComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Categories', () => {
        navBarPage.goToEntity('category');
        categoryComponentsPage = new CategoryComponentsPage();
        expect(categoryComponentsPage.getTitle()).toMatch(/Categories/);

    });

    it('should load create Category dialog', () => {
        categoryComponentsPage.clickOnCreateButton();
        categoryDialogPage = new CategoryDialogPage();
        expect(categoryDialogPage.getModalTitle()).toMatch(/Create or edit a Category/);
        categoryDialogPage.close();
    });

    it('should create and save Categories', () => {
        categoryComponentsPage.clickOnCreateButton();
        categoryDialogPage.setCategoryNameInput('categoryName');
        expect(categoryDialogPage.getCategoryNameInput()).toMatch('categoryName');
        categoryDialogPage.save();
        expect(categoryDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CategoryComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-category div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class CategoryDialogPage {
    modalTitle = element(by.css('h4#myCategoryLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    categoryNameInput = element(by.css('input#field_categoryName'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setCategoryNameInput = function (categoryName) {
        this.categoryNameInput.sendKeys(categoryName);
    }

    getCategoryNameInput = function () {
        return this.categoryNameInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
