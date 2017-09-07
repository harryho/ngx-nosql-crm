import { BaseEntity } from './../../shared';

export class Category implements BaseEntity {
    constructor(
        public id?: string,
        public categoryName?: string,
    ) {
    }
}
