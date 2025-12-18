import { z } from "zod";

export const emailSchema = z
  .string({ required_error: "Email is required" })
  .trim()
  .min(1, "Email is required")
  .max(255, "Email must be 255 characters or less")
  .email("Email is invalid");

export const passwordSchema = z
  .string({ required_error: "Password is required" })
  .min(8, "Password must be at least 8 characters")
  .max(72, "Password must be 72 characters or less")
  .refine((v) => !/\s/.test(v), "Password must not contain whitespace")
  .refine((v) => !/[<>]/.test(v), "Password must not contain '<' or '>'");

export const loginSchema = z.object({
  email: emailSchema,
  password: passwordSchema,
});

export const registerSchema = loginSchema;

export const quantitySchema = z
  .number()
  .int()
  .min(1, "Quantity must be >= 1")
  .max(99, "Quantity must be <= 99");

export type LoginInput = z.infer<typeof loginSchema>;
export type RegisterInput = z.infer<typeof registerSchema>;

