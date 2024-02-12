import { ApiProperty } from '@nestjs/swagger';
import {
  IsBoolean,
  IsDate,
  IsInt,
  IsNotEmpty,
  IsString,
} from 'class-validator';

export class CreateBookDto {
  @IsString()
  @ApiProperty({ name: 'author', example: 'Kent Beck' })
  readonly author: string;

  @IsString()
  @IsNotEmpty({ message: 'Gagal menambahkan buku. Mohon isi nama buku' })
  @ApiProperty({ name: 'name', example: 'Extreme Programming Explained' })
  readonly name: string;

  @IsInt()
  @ApiProperty({ name: 'pageCount', example: 0 })
  readonly pageCount: number;

  @IsString()
  @ApiProperty({ name: 'publisher', example: "O'Reilly" })
  readonly publisher: string;

  @IsInt()
  @ApiProperty({ name: 'readPage', example: 0 })
  readonly readPage: number;

  @IsBoolean()
  @ApiProperty({ name: 'reading', example: false })
  readonly reading: boolean = false;

  @IsString()
  @ApiProperty({
    name: 'summary',
    example: `Software development projects can be fun, productive, and even daring. Yet they can consistently deliver value to a business and remain under control.

Extreme Programming (XP) was conceived and developed to address the specific needs of software development conducted by small teams in the face of vague and changing requirements. This new lightweight methodology challenges many conventional tenets, including the long-held assumption that the cost of changing a piece of software necessarily rises dramatically over the course of time. XP recognizes that projects have to work to achieve this reduction in cost and exploit the savings once they have been earned.`,
  })
  readonly summary: string;

  @IsDate()
  @ApiProperty({
    name: 'year',
    example: new Date(),
  })
  readonly year: Date;
}
