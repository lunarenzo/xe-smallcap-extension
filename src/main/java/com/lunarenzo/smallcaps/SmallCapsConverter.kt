package com.lunarenzo.smallcaps

/**
 * Utility object for performing high-performance, memory-efficient string
 * and character transformations to Unicode Small Caps text.
 */
object SmallCapsConverter {

    /**
     * Primitive ASCII lookup array mapping standard Latin letters (a-z, A-Z)
     * to their corresponding Unicode Small Capital equivalents.
     */
    private val SMALL_CAPS_MAP = CharArray(128).apply {
        // Initialize default identity mapping for standard ASCII range
        for (i in 0 until 128) {
            this[i] = i.toChar()
        }

        // Lowercase Latin to Small Caps mapping
        this['a'.code] = 'ᴀ'
        this['b'.code] = 'ʙ'
        this['c'.code] = 'ᴄ'
        this['d'.code] = 'ᴅ'
        this['e'.code] = 'ᴇ'
        this['f'.code] = 'ғ'
        this['g'.code] = 'ɢ'
        this['h'.code] = 'ʜ'
        this['i'.code] = 'ɪ'
        this['j'.code] = 'ᴊ'
        this['k'.code] = 'ᴋ'
        this['l'.code] = 'ʟ'
        this['m'.code] = 'ᴍ'
        this['n'.code] = 'ɴ'
        this['o'.code] = 'ᴏ'
        this['p'.code] = 'ᴘ'
        this['q'.code] = 'ǫ'
        this['r'.code] = 'ʀ'
        this['s'.code] = 'ѕ'
        this['t'.code] = 'ᴛ'
        this['u'.code] = 'ᴜ'
        this['v'.code] = 'ᴠ'
        this['w'.code] = 'ᴡ'
        this['x'.code] = 'x'
        this['y'.code] = 'ʏ'
        this['z'.code] = 'ᴢ'

        // Uppercase Latin to Small Caps mapping (case-insensitive conversion)
        this['A'.code] = 'ᴀ'
        this['B'.code] = 'ʙ'
        this['C'.code] = 'ᴄ'
        this['D'.code] = 'ᴅ'
        this['E'.code] = 'ᴇ'
        this['F'.code] = 'ғ'
        this['G'.code] = 'ɢ'
        this['H'.code] = 'ʜ'
        this['I'.code] = 'ɪ'
        this['J'.code] = 'ᴊ'
        this['K'.code] = 'ᴋ'
        this['L'.code] = 'ʟ'
        this['M'.code] = 'ᴍ'
        this['N'.code] = 'ɴ'
        this['O'.code] = 'ᴏ'
        this['P'.code] = 'ᴘ'
        this['Q'.code] = 'ǫ'
        this['R'.code] = 'ʀ'
        this['S'.code] = 'ѕ'
        this['T'.code] = 'ᴛ'
        this['U'.code] = 'ᴜ'
        this['V'.code] = 'ᴠ'
        this['W'.code] = 'ᴡ'
        this['X'.code] = 'x'
        this['Y'.code] = 'ʏ'
        this['Z'.code] = 'ᴢ'
    }

    /**
     * Converts a single character [ch] into its Small Caps equivalent.
     * Non-ASCII or non-letter characters are returned unchanged.
     */
    fun convertChar(ch: Char): Char {
        val code = ch.code
        return if (code in 0..127) SMALL_CAPS_MAP[code] else ch
    }

    /**
     * Transforms an entire input sequence into Small Caps text.
     * Operates with O(N) time complexity and single StringBuilder allocation.
     */
    fun toSmallCaps(input: CharSequence): String {
        if (input.isEmpty()) return ""
        val sb = StringBuilder(input.length)
        for (i in 0 until input.length) {
            sb.append(convertChar(input[i]))
        }
        return sb.toString()
    }
}
