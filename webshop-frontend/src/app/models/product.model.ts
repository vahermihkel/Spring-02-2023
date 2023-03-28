import { Category } from "./category.model";

export class Product {
    constructor(
        public id: number,
        public name: string,
        public image: string,
        public price: number,
        public description: string,
        public active: boolean,
        public stock: number,
        public category?: Category
    ) {}
}