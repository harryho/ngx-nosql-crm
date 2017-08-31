import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';

import {
    NgxNosqlCrmSharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

@NgModule({
    imports: [
        NgxNosqlCrmSharedLibsModule
    ],
    declarations: [
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
    ],
    exports: [
        NgxNosqlCrmSharedLibsModule,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class NgxNosqlCrmSharedCommonModule {}
