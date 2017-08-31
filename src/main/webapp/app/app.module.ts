import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { NgxNosqlCrmSharedModule, UserRouteAccessService } from './shared';
import { NgxNosqlCrmHomeModule } from './home/home.module';
import { NgxNosqlCrmAdminModule } from './admin/admin.module';
import { NgxNosqlCrmAccountModule } from './account/account.module';
import { NgxNosqlCrmEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        NgxNosqlCrmSharedModule,
        NgxNosqlCrmHomeModule,
        NgxNosqlCrmAdminModule,
        NgxNosqlCrmAccountModule,
        NgxNosqlCrmEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class NgxNosqlCrmAppModule {}
