package com.discovr.discord.ui.main;

public interface MainAction {
    final class RollDice implements MainAction {
        public RollDice() {
        }
    }

    final class SwipeLeft implements MainAction {
        private final int position;

        public SwipeLeft(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    final class SwipeRight implements MainAction {
        private final int position;

        public SwipeRight(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    final class LoadCards implements MainAction {
    }
}
