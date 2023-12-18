package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.BlockNode;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class APLang extends LanguageProvider {
    public APLang(PackOutput pack) {
        super(pack, ArchitectsPalette.MOD_ID, "auto_generated");
    }

    @Override
    protected void addTranslations() {
        BlockNode.forAllBaseNodes((node) -> {
            node.forEach( n -> {
                add(n.get(), format(n.getName()));
            });
        });
    }

    private static String format(String name) {
        String filtered = name.replaceAll("_", " ");
        String upper = name.toUpperCase();
        int len = filtered.length();

        char[] buffer = new char[len];
        //Set first character to uppercase
        buffer[0] = upper.charAt(0);
        for (int i = 1; i < len; i++) {
            //Check for a space preceding this character
            if (filtered.charAt(i - 1) == ' ') {
                //Use uppercase after spaces
                buffer[i] = upper.charAt(i);
            }
            //Use normal text
            else {
                buffer[i] = filtered.charAt(i);
            }
        }
        return new String(buffer);
    }

}
